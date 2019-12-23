package com.fs.wegit.source

import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.lib.rx.RxUtil
import com.fs.wegit.model.RepoItemEntity
import com.fs.wegit.service.GithubService
import io.reactivex.Observable
import retrofit2.Response

class RepoSource {


    fun getRepoItem(repoUrl: String): Observable<Response<RepoItemEntity>> {

        return RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .getRepoItem(repoUrl)
            .compose(RxUtil.mainIO())
    }

    fun getReadmeText(url: String): Observable<Response<String>> {

        return RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .getFileList(url)
            .compose(RxUtil.mainIO())
            .map {
                it.body()?.forEach { item ->

                    if (item.name == "README.md") {
                        return@map item.download_url
                    }
                }
                ""
            }
            .flatMap {
                if (it.isNotEmpty()) {
                    RetrofitManager.instance.createApi(GithubService.GithubService::class.java)
                        .getStringText(it).compose(RxUtil.mainIO())
                } else Observable.just(Response.success(""))
            }

    }
}
