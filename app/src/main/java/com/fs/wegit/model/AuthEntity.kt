package com.fs.wegit.model

data class AuthEntity(
    val app: App,
    val created_at: String,
    val fingerprint: Any,
    val hashed_token: String,
    val id: Int,
    val note: String,
    val note_url: Any,
    val scopes: List<Any>,
    val token: String,
    val token_last_eight: String,
    val updated_at: String,
    val url: String
)

data class App(
    val client_id: String,
    val name: String,
    val url: String
)