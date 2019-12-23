package com.fs.lib.http

import android.util.Log
import com.fs.lib.http.retrofit.RetrofitManager
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpUtil private constructor() {

    companion object {
        val instance = OkHttpUtil()
    }


    private val client = OkHttpClient.Builder()
        .addInterceptor {
            var response = it.proceed(it.request())
            val s = response.headers()["Location"]
            val code = response.headers()["Status"]
            Log.d("TAG", " Status   $code")
            Log.d("TAG", " Location  $s")
            Log.d("TAG", "${response.header("Expect-CT")}")
            Log.d("TAG", "${response.priorResponse()?.header("Location")}")

//            val newRequest = it.request().newBuilder().url(s).build()
//            response = it.proceed(newRequest)
            response
        }
        .addInterceptor(getLogger())
        .build()
    
    
    fun get() {
        RetrofitManager
    }


    fun get(url: String, callback: Callback) {
        val request = Request
            .Builder()
            .url(url)
            .addHeader("Cookie", "_octo=GH1.1.2030723879.1508756907; " +
                    "_ga=GA1.2.320494309.1508756907; user_session=Mm-69bvyikZV_kkEGw1lRS7qpqYfsjWUUpoxFHJ8Jf7TZbJw; " +
                    "__Host-user_session_same_site=Mm-69bvyikZV_kkEGw1lRS7qpqYfsjWUUpoxFHJ8Jf7TZbJw; logged_in=yes; dotcom_user=Funsquirrel;" +
                    " _device_id=222e26fc0eee3a58f03f1c00e86264ce; tz=Asia%2FShanghai; _gid=GA1.2.502241999.1555677558; ignored_unsupported_browser_notice=false; has_recent_activity=1; _gat=1;" +
                    " _gh_sess=SjNXcFZteTYwQy9leVhKTGRsWkx4RDB3ZGxWUDhkUEhoeGZwMUJ5STdYUjVKNkc0QXdhcWVPR2xCTHZyc1ZpNm8vbG9rVVR1TWUzc1lkMk9WVlJtZWlZWGYxQklLNTA1d0JqeG1JbnBZQ2FwamNoUmZPNjV5UTFwWTdoNzlKTmo0Wk5MSGlnSWpKd29BWlBPd3ZHTjFCbEplSGJyNEZUczFxNDc2MFpNRXM4aGpkMS9MM0NqRzdlZlAwSnhaQzB1S3Qra1dEbCtSN21ydWVxejIrZGFoWVA0YVd2bjJsN1MvQnBhWlowU281ZHVKaTRzSUFOS1FZREtYQ29TL3BrcjBHR29EN1ZRekNQL0l5UGkwSFBRbEk5eTJMU0c4MExzbnZXTk5GcVp4dWxRTDZybHI0SEs5YWxib0dISGJxS1p6NnJHMmFQY1hnUXFjZ1FNZ3NRam1LTzdHUEtYWlJsTWVqNFVMWVBoMHRBUnd4R01qSGZwSGNxb3duK29YaUxJZHZnalJESERaQzhsN2tqN3Z0T0N2OWU0QlVVMTZoQmY0UnJiZkVFNWpjdz0tLXdIbE83UGV3enhSSHlPYnpranFxVHc9PQ%3D%3D--27e092c7e8e8e8ed0a10f8f23e3e02d392a55122")
            .build()
        client.newCall(request).enqueue(callback)
    }

    private fun getLogger(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Log.d("TAG", "----------> $message")
        })
        logger.level = HttpLoggingInterceptor.Level.HEADERS
        return logger
    }

}
