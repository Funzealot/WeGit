package com.fs.lib.http.okutil

import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class OkHttpConfig {

    var client: OkHttpClient? = OkHttpClient()
        private set

    object DefaultConfig {
        private val clientBuilder = OkHttpClient.Builder()

        val defaultConfig = Builder()
            .setClient(clientBuilder.build())

    }

    companion object {
        val trustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {

                return emptyArray()
            }
        }

    }

    class Builder {

        private val config = OkHttpConfig()

        fun setClient(client: OkHttpClient) {
            config.client = client
        }

        fun build() {

        }
    }


}