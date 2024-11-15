package com.akma.githubusersearch.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.akma.githubusersearch.R
import com.akma.githubusersearch.adapters.UserRepositoryAdapter
import com.akma.githubusersearch.adapters.UserSearchResultAdapter
import com.akma.githubusersearch.app.GitHubUserSearchApp
import com.akma.githubusersearch.databinding.FragmentProfileBinding
import com.akma.githubusersearch.factory.GenericVMFactory
import com.akma.githubusersearch.ui.GitHubViewModel
import com.akma.githubusersearch.utils.Loader
import com.akma.githubusersearch.utils.NetworkResponse
import com.akma.githubusersearch.utils.Utill
import com.bumptech.glide.Glide
import javax.inject.Inject

class ProfileFragment : Fragment() {
    @Inject
    lateinit var genericVMFactory: GenericVMFactory
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GitHubViewModel
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (this.requireContext().applicationContext as GitHubUserSearchApp).appComponent.injectInProfileFragment(
            this
        )
        viewModel = ViewModelProvider(this.requireActivity(), genericVMFactory).get(GitHubViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        username = arguments?.getString("username") ?: "AkashSingh1505"
        viewModel.fetchUser(username)
        viewModel.fetchUserRepos(username)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner){response->

            when (response) {
                is NetworkResponse.Success -> {
                    response.body?.run {
                        with(binding) {
                            usernameTV.text = name ?: "N/A"
                            userHandle.text = "@${login}" ?: "N/A"
                            bioTV.text = (bio ?: "No bio available").toString()
                            joinedDate.text = "Joined ${Utill.formatDate(created_at!!) ?: 0}"
                            followingCount.text = (following ?: "0").toString()
                            followersCount.text = (followers ?: 0).toString()
                            reposCount.text = (public_repos ?: 0).toString()
                            Glide.with(profileImage.context)
                                .load(avatar_url)
                                .into(profileImage)
                        }
                        Loader.hideProgress()
                    }
                }

                is NetworkResponse.Error -> {
                    Utill.showToast(this.requireContext(), response.errorMsz)
                    Loader.hideProgress()
                }

                is NetworkResponse.Loading -> {
                    Loader.showProgress(this.requireContext(),null)
                }

            }
        }
        viewModel.repo.observe(viewLifecycleOwner){response->
            when (response) {
                is NetworkResponse.Success -> {
                    response.body?.run {
                        with(binding){
                            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                            repositoriesRecyclerView.layoutManager = staggeredGridLayoutManager
                            repositoriesRecyclerView.adapter = UserRepositoryAdapter(this@run) // Initialize your a
                        }
                        Loader.hideProgress()
                    }
                }

                is NetworkResponse.Error -> {
                    Utill.showToast(this.requireContext(), response.errorMsz)
                    Loader.hideProgress()
                }

                is NetworkResponse.Loading -> {
                    Loader.showProgress(this.requireContext(),null)
                }


            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}