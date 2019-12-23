package com.fs.wegit.source

import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.lib.rx.RxUtil
import com.fs.wegit.model.RepoListEntity
import com.fs.wegit.service.GithubService
import io.reactivex.Observable
import retrofit2.Response

class RepoListSource {

    fun getRepoList(url: String): Observable<Response<RepoListEntity>> {

        return RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .getRepoList(url)
            .compose(RxUtil.mainIO())
    }
}