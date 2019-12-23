package com.fs.lib.http.okutil

import okhttp3.Response

class UrlResponse {


    lateinit var response: Response

    class Builder(var builder: Response.Builder) {


        val response: UrlResponse = UrlResponse()

        fun addHeader() {

        }

        fun build(): UrlResponse {
            response.response =  builder.build()
            return response
        }
    }
}