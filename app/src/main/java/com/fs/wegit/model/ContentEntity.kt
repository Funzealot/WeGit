package com.fs.wegit.model

import java.io.Serializable


data class FileItemEntity(
    val _links: Links,
    val download_url: String,
    val git_url: String,
    val html_url: String,
    val name: String,
    val path: String,
    val sha: String,
    val size: Int,
    val type: String,
    val url: String
) : Serializable


data class Links(
    val git: String,
    val html: String,
    val self: String
) : Serializable


