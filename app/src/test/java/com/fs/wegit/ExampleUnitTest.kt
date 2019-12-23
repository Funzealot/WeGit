package com.fs.wegit

import com.fs.wegit.model.AuthEntity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.*
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test

    fun authTest() {

        val s = Credentials.basic("Funsquirrel", "zhangfan736")
//        println(s)

        val model: Model = Model(
            arrayListOf(),
            "com.fs.wegit",
            "Iv1.119410d8775d8b32",
            "942714a8a31dcad32ed6602532fe5954ee0c8f95"
        )

        val gson = Gson()


        val ok = OkHttpClient.Builder().build()
        val requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(model))


        val request = Request.Builder()
            .url("https://api.github.com/authorizations")
            .method("POST", requestBody)
            .addHeader("Authorization", "Basic RnVuc3F1aXJyZWw6emhhbmdmYW43MzY=")
            .build()


        val response = ok.newCall(request)
            .execute()



    }


    @Test
    fun reTest() {

        val model: Model = Model(
            arrayListOf(),
            "com.fs.wegit",
            "Iv1.119410d8775d8b32",
            "942714a8a31dcad32ed6602532fe5954ee0c8f95"
        )


        val retro: Retrofit = Retrofit
            .Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com")
            .build()


//        val body = retro.create(Service::class.java)
//            .auth("Basic RnVuc3F1aXJyZWw6emhhbmdmYW43MzY=", model)
//            .execute()



        // b8b90e6af9dca643b39fefbb7dc12020fe7cc500
        println("-----------------------")

        val token = "11f06fc97fbb5ec74bed3e6fd99e374f28b43b1b"
        val user = retro.create(Service::class.java)
            .user("token $token")
            .execute()


        println(user.code())
        println(user.body())


    }

    interface Service {


        @POST("/authorizations")
        fun auth(@Header("Authorization") header: String, @Body body: Model): Call<AuthEntity>


        @GET
        fun get(@Url url: String): Call<String>

        @GET("/user")
        fun user(@Header("Authorization") header: String): Call<String>
    }


    data class Gank(
        val error: Boolean,
        val results: List<Result>
    )

    data class Result(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
    )
}

data class Model(
    val scopes: List<String>,
    val note: String,
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSec: String
) {

}
