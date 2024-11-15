package com.akma.githubusersearch.adapters

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akma.githubusersearch.data.UserSearchResponseItem
import com.akma.githubusersearch.retrofit.GitHubApiService
import java.io.IOException
import javax.inject.Inject

class UserSearchPagingSource(
    private val apiService: GitHubApiService,
    private val query: String
) : PagingSource<Int, UserSearchResponseItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserSearchResponseItem> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchUsers(query, page, params.loadSize)

            if (response.isSuccessful) {
                val body = response.body()
                val items = body?.items?.filterNotNull() ?: emptyList() // Filter out nulls

                if (items.isEmpty()) {
                    return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
                }

                LoadResult.Page(
                    data = items,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (items.isEmpty()) null else page + 1
                )
            } else {
                throw Exception(response.message())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserSearchResponseItem>): Int? = null
}
