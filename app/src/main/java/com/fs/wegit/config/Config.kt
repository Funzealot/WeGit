package com.fs.wegit.config

import com.fs.lib.http.okutil.OkHttpConfig
import com.fs.lib.http.okutil.SSLSocketFactoryCompat
import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.wegit.service.GithubService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

object Config {

    fun initRetrofitManager() {

        val client = OkHttpClient
            .Builder()
            .callTimeout(5000, TimeUnit.SECONDS)
            .connectTimeout(3000, TimeUnit.SECONDS)
            .sslSocketFactory(SSLSocketFactoryCompat(OkHttpConfig.trustManager), OkHttpConfig.trustManager)
            .build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(GithubService.API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        RetrofitManager.initRetrofitManager(retrofit)

    }
}