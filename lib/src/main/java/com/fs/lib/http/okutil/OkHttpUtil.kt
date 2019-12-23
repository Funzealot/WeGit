package com.fs.lib.http.okutil

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpUtil private constructor() {

    private lateinit var config: OkHttpConfig
    var client: OkHttpClient? = null
        private set
    private var builder: OkHttpClient.Builder? = null

//    private fun initClient(): OkHttpClient{
//        return builder.build()
//    }


    private val interceptors = ArrayList<Interceptor>()

    val headerMap = HashMap<String, String>()


    fun addGlobalHeader(name: String, value: String) {
        headerMap[name] = value
    }

    fun get(url: String): UrlRequest {

        return UrlRequest(Request.Builder().get().url(url))
    }

    fun post() {
    }

//    private fun doPreRequest() {
//        val builder = client?.newBuilder()
//        interceptors.forEach {
//            builder?.addNetworkInterceptor(it)
//        }
//    }

//    fun addGlobalInterceptor(interceptor: Interceptor) {
//
//        builder = builder ?: let { client?.newBuilder() }
//        interceptors.add(interceptor)
//        builder?.addNetworkInterceptor(interceptor)
//    }

    private object OkHttpUtilInstanceHolder {
        val instance: OkHttpUtil = OkHttpUtil()
    }

    companion object {
        val instance = OkHttpUtilInstanceHolder.instance
        private var isInitialized = false

        fun initOkHttpClient(client: OkHttpClient): OkHttpUtil {
            if (!isInitialized) {
                isInitialized = true
                instance.client = client
            }
            return instance
        }


        fun initOkHttpUtil(config: OkHttpConfig) {
            instance.config = config
        }

        fun initOkHttpUtil(context: Context) {

        }
    }


}
