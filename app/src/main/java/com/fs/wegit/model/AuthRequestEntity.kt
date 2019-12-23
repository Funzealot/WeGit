package com.fs.wegit.model

import com.fs.wegit.App
import com.fs.wegit.BuildConfig
import com.fs.wegit.R
import com.google.gson.annotations.SerializedName

data class AuthRequestEntity(
    val scopes: List<String>,
    val note: String,
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSec: String
) {
    companion object {

        fun generate() = AuthRequestEntity(
            arrayListOf(),
            App.instance.getString(R.string.app_name),
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET
        )
    }
}