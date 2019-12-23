package com.fs.lib.http.retrofit

import retrofit2.Retrofit

class RetrofitManager private constructor() {


    private var retrofit: Retrofit? = null
    var token: String = ""

    private object InstanceHolder {
        val instance = RetrofitManager()
    }


    companion object {
        val instance = InstanceHolder.instance

        fun initRetrofitManager(retrofit: Retrofit) {


            instance.retrofit = retrofit
        }


        fun initRetrofitManager() {

        }
    }


    fun <T> createApi(cls: Class<T>): T {

        return retrofit!!.create(cls)
    }
}
