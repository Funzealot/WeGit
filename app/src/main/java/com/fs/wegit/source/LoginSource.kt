package com.fs.wegit.source

import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.lib.rx.RxUtil
import com.fs.lib.state.Resource
import com.fs.wegit.constant.Constant
import com.fs.wegit.model.AuthEntity
import com.fs.wegit.model.AuthRequestEntity
import com.fs.wegit.service.GithubService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.Credentials
import retrofit2.Response

class LoginSource {

    fun auth(userName: String, password: String): Observable<Resource<AuthEntity>> {

        val token = Credentials.basic(userName, password)
        val body = AuthRequestEntity.generate()
        return RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .auth(token, body)
            .compose(RxUtil.mainIO())
            .map {
                if (it.code() == 201)
                    Resource.success(it.body())
                else
                    Resource.error("授权出错")
            }
    }

}
