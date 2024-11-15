package com.akma.githubusersearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.akma.githubusersearch.data.UserSearchResponseItem
import com.akma.githubusersearch.repository.GitHubRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class GitHubViewModel @Inject constructor(val repository: GitHubRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val user = repository.user
    val repo = repository.repos

    val users: LiveData<PagingData<UserSearchResponseItem>> = _query.switchMap { query ->
        repository.searchUsers(query).liveData.cachedIn(viewModelScope)
    }

    fun searchUsers(query: String) {
        if (_query.value != query) {
            _query.value = query
        }
    }


    fun fetchUser(username: String) {
        viewModelScope.launch {
            repository.getUser(username)
        }
    }

    fun fetchUserRepos(username: String) {
        viewModelScope.launch {
            repository.getRepo(username)
        }
    }

}