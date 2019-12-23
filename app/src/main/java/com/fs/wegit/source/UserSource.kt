package com.fs.wegit.source

import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.lib.rx.RxUtil
import com.fs.lib.http.retrofit.UserEntity
import com.fs.wegit.service.GithubService
import io.reactivex.Observable
import retrofit2.Response

class UserSource {
    
    fun getUser(userName: String): Observable<Response<UserEntity>> {
        return RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .getUser(userName)
            .compose(RxUtil.mainIO())
    }
}
