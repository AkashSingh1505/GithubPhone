package com.akma.githubusersearch.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.akma.githubusersearch.adapters.UserSearchResultAdapter
import com.akma.githubusersearch.app.GitHubUserSearchApp
import com.akma.githubusersearch.databinding.FragmentSearchBinding
import com.akma.githubusersearch.factory.GenericVMFactory
import com.akma.githubusersearch.ui.GitHubViewModel
import com.akma.githubusersearch.utils.Loader
import com.akma.githubusersearch.utils.NetworkResponse
import com.akma.githubusersearch.utils.Utill
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {
    val TAG = SearchFragment::class.simpleName

    @Inject
    lateinit var genericVMFactory: GenericVMFactory
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GitHubViewModel
    lateinit var adapter : UserSearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (this.requireContext().applicationContext as GitHubUserSearchApp).appComponent.injectInSearchFragment(
            this
        )
        viewModel = ViewModelProvider(
            this.requireActivity(),
            genericVMFactory
        ).get(GitHubViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = UserSearchResultAdapter(findNavController())
        binding.recyclerView.adapter =adapter
        adapter.addLoadStateListener { loadStates ->
            when (val refreshState = loadStates.refresh) {
                is LoadState.Error -> {
                    val errorMessage = refreshState.error.localizedMessage ?: "An error occurred"
                    Utill.showSnackBar(this@SearchFragment.requireView(), errorMessage)
                }
                is LoadState.NotLoading -> {
                    if (adapter.itemCount == 0) {
                        Utill.showSnackBar(this@SearchFragment.requireView(), "User not found")
                    }
                }
                else -> Unit
            }
        }
        binding.usernameInput.addTextChangedListener(object : TextWatcher {
            private var debounceJob: Job? = null

            override fun afterTextChanged(s: Editable?) {
                debounceJob?.cancel()
                if (!s.isNullOrEmpty()) {
                    debounceJob = lifecycleScope.launch {
                        delay(300)
                        viewModel.searchUsers(s.toString())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.users.observe(viewLifecycleOwner) { pagingData ->

            adapter.submitData(lifecycle, pagingData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}