package com.fs.wegit.repository

import com.fs.lib.state.Resource
import com.fs.wegit.model.RepoItemEntity
import com.fs.wegit.source.RepoSource
import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.Response

class RepoPageRepo(private val repoSource: RepoSource) {

    fun getRepoItem(repoUrl: String): Observable<Resource<RepoItemEntity>> {

        return repoSource.getRepoItem(repoUrl)
            .map {
                Resource.fromResponse(it)
            }
    }


    fun getReadme(url: String): Observable<Resource<String>> {

        return repoSource.getReadmeText(url)
            .flatMap(Function<Response<String>, Observable<Resource<String>>> { response ->
                if (response.code() == 200) {
                    response.body()?.let {
                        return@Function if (it.isNotEmpty())
                            Observable.just(Resource.success(it))
                        else Observable.just(Resource.success(""))

                    }
                }
                return@Function Observable.just(Resource.success(""))
            })
    }

/*
    fun getRepoPage(repoUrl: String, readmeUrl: String): Observable<Resource<RepoPageEntity>> {


        return getRepoItem(repoUrl)
            .zipWith(getReadme(""), BiFunction { t1, t2 ->
                RepoPageEntity(t1.data, t2.data)
            })


    }
*/
}
