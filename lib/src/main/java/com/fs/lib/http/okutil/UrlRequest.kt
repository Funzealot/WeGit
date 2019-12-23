package com.fs.lib.http.okutil

import io.reactivex.Observable
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response

class UrlRequest(private var builder: Request.Builder) {


    private var request: Request = builder.build()
    private var urlBuilder: HttpUrl.Builder? = null


    fun addHeader(name: String, value: String): UrlRequest {
        builder.addHeader(name, value)
        return this
    }

    fun addParam(name: String, value: String): UrlRequest {
        urlBuilder = urlBuilder ?: let { request.url().newBuilder() }
        urlBuilder?.addQueryParameter(name, value)
        return this
    }

    private fun addGlobalHeaders() {

        val map = OkHttpUtil.instance.headerMap

        if (map.isNotEmpty()) {
            for (key: String in map.keys) {
                builder.addHeader(key, map[key]!!)
            }
        }
    }

    private fun doPreRequest() {
        addGlobalHeaders()
        builder.url(urlBuilder!!.build())

    }

    fun call() {
        val c = OkHttpUtil.instance.client?.newCall(builder.build())
    }

    fun execute(): Response? {
        doPreRequest()
        builder.url(urlBuilder!!.build())
        return OkHttpUtil.instance.client?.newCall(builder.build())?.execute()
    }

    fun synExecute(callback: Callback) {
        doPreRequest()
        OkHttpUtil.instance.client?.newCall(builder.build())?.enqueue(callback)
    }

    fun applyRx(): Observable<Response> {
        doPreRequest()

        return Observable.create<Response> {
            val res = OkHttpUtil.instance.client?.newCall(builder.build())?.execute()
            it.onNext(res!!)
        }
    }

    fun getEntity() {

    }


}