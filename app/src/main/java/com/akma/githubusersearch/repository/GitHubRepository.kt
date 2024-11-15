package com.akma.githubusersearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.akma.githubusersearch.R
import com.akma.githubusersearch.adapters.UserSearchPagingSource
import com.akma.githubusersearch.app.GitHubUserSearchApp
import com.akma.githubusersearch.data.Repos
import com.akma.githubusersearch.data.User
import com.akma.githubusersearch.data.UserSearchResponse
import com.akma.githubusersearch.data.UserSearchResponseItem
import com.akma.githubusersearch.retrofit.GitHubApiService
import com.akma.githubusersearch.utils.NetworkResponse
import com.akma.githubusersearch.utils.Utill
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val applicaiton: GitHubUserSearchApp,
    private val gitHubApiService: GitHubApiService
) {
    private val _user =
        MutableLiveData<NetworkResponse<User>>()
    val user: LiveData<NetworkResponse<User>>
        get() = _user

    private val _repos =
        MutableLiveData<NetworkResponse<Repos>>()
    val repos: LiveData<NetworkResponse<Repos>>
        get() = _repos


    suspend fun getUser(userName:String) {
        _user.postValue(NetworkResponse.Loading())
        if (Utill.isNetworkAvailable(applicaiton)) {
            val response = gitHubApiService.getUser(userName)
            if (response.isSuccessful && response.body() != null) {
                if (response.code() != 404) {
                    _user.postValue(NetworkResponse.Success(response.body()!!))

                } else {
                    _user.postValue(
                        NetworkResponse.Error(
                            response.message(),
                            response.body()
                        )
                    )
                    return
                }
            } else {
                _user.postValue(
                    NetworkResponse.Error(
                        response.message(),
                        response.body()
                    )
                )
            }
        } else {
            _user.postValue(
                NetworkResponse.Error(
                    applicaiton.resources.getString(R.string.no_network)
                )
            )

        }
        try {
        } catch (e: Exception) {
            Log.e("E", e.message.toString())
        }
    }

    fun searchUsers(query: String): Pager<Int, UserSearchResponseItem> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                initialLoadSize = 15
            ),
            pagingSourceFactory = { UserSearchPagingSource(gitHubApiService, query) }
        )
    }

    suspend fun getRepo(userName:String) {
        _repos.postValue(NetworkResponse.Loading())
        if (Utill.isNetworkAvailable(applicaiton)) {
            val response = gitHubApiService.getUserRepos(userName)
            if (response.isSuccessful && response.body() != null) {
                if (response.code() != 404) {
                    _repos.postValue(NetworkResponse.Success(response.body()!!))

                } else {
                    _repos.postValue(
                        NetworkResponse.Error(
                            response.message(),
                            response.body()
                        )
                    )
                    return
                }
            } else {
                _repos.postValue(
                    NetworkResponse.Error(
                        response.message(),
                        response.body()
                    )
                )
            }
        } else {
            _repos.postValue(
                NetworkResponse.Error(
                    applicaiton.resources.getString(R.string.no_network)
                )
            )

        }
        try {
        } catch (e: Exception) {
            Log.e("E", e.message.toString())
        }
    }
}
