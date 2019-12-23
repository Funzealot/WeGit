package com.fs.wegit.service

import com.fs.lib.http.retrofit.UserEntity
import com.fs.wegit.model.*
import com.jakewharton.retrofit2.adapter.rxjava2.Result
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

class GithubService {

    companion object {

        const val API_BASE_URL = "https://api.github.com"
        const val IDENTIFY = "https://github.com/login/oauth/authorize?client_id=Iv1.119410d8775d8b32"

    }

    interface GithubService {


        @GET("/users/{userName}")
        fun getUser(@Path("userName") userName: String): Observable<Response<UserEntity>>


        @GET("/users/{userName}")
        fun getUser2(@Path("userName") userName: String): Observable<Result<com.fs.wegit.model.UserEntity>>


        @GET("/users/Funsquirrel")
        fun getString(): Call<UserEntity>


        @GET("/search/repositories")
        fun getRepoList(@Query("q") q: String): Observable<Response<RepoListEntity>>

        @GET("/users/{userName}/followers")
        fun getFollowers(@Path("userName") userName: String): Observable<List<FollowUser>>

        @GET("/users/{userName}/following")
        fun getFollowing(@Path("userName") url: String): Observable<List<FollowUser>>

        @GET("/users/{userName}/following/{targetName}")
        fun checkFollowing(@Path("userName") userName: String, @Path("targetName") targetName: String): Observable<Response<String>>


        @GET
        fun getFileList(@Url url: String): Observable<Response<List<FileItemEntity>>>

        @GET
        fun getRepoItem(@Url repoUrl: String): Observable<Response<RepoItemEntity>>

        @GET
        fun getStringText(@Url url: String): Observable<Response<String>>


        @POST("/authorizations")
        fun auth(@Header("Authorization") token: String, @Body authRequestEntity: AuthRequestEntity): Observable<Response<AuthEntity>>


        @GET("/user")
        fun getAuthUser(@Header("Authorization") token: String): Observable<Response<AuthUserEntity>>

    }

}
