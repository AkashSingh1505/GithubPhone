package com.akma.githubusersearch.retrofit

import com.akma.githubusersearch.data.Repos
import com.akma.githubusersearch.data.User
import com.akma.githubusersearch.data.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<User>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): Response<Repos>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Response<UserSearchResponse>

}