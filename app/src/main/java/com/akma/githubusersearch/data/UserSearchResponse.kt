package com.akma.githubusersearch.data

data class UserSearchResponse(
    val incomplete_results: Boolean?,
    val items: List<UserSearchResponseItem?>?,
    val total_count: Int?
)

data class UserSearchResponseItem(
    val avatar_url: String?,
    val events_url: String?,
    val followers_url: String?,
    val following_url: String?,
    val gists_url: String?,
    val gravatar_id: String?,
    val html_url: String?,
    val id: Int?,
    val login: String?,
    val node_id: String?,
    val organizations_url: String?,
    val received_events_url: String?,
    val repos_url: String?,
    val score: Double?,
    val site_admin: Boolean?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val type: String?,
    val url: String?,
    val user_view_type: String?
)
