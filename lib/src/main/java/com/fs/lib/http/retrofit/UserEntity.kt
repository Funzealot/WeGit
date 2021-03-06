package com.fs.lib.http.retrofit

data class UserEntity(
    var avatar_url: String?,
    var bio: String?,
    var blog: String?,
    var company: String?,
    var created_at: String?,
    var email: String?,
    var events_url: String?,
    var followers: Int?,
    var followers_url: String?,
    var following: Int?,
    var following_url: String?,
    var gists_url: String?,
    var gravatar_id: String?,
    var hireable: String?,
    var html_url: String?,
    var id: Int?,
    var location: String?,
    var login: String?,
    var name: String?,
    var node_id: String?,
    var organizations_url: String?,
    var public_gists: Int?,
    var public_repos: Int?,
    var received_events_url: String?,
    var repos_url: String?,
    var site_admin: Boolean?,
    var starred_url: String?,
    var subscriptions_url: String?,
    var type: String?,
    var updated_at: String?,
    var url: String?,

    var ok: String? = ""

){
    override fun toString(): String {
        return "$login\n $html_url \n "
    }
}
