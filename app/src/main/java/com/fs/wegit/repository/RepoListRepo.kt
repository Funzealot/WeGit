package com.fs.wegit.repository

import com.fs.lib.state.Resource
import com.fs.wegit.model.RepoListEntity
import com.fs.wegit.source.RepoListSource
import io.reactivex.Observable

class RepoListRepo constructor(private val repoListSource: RepoListSource) {

    fun getRepoList(q: String): Observable<Resource<RepoListEntity>> {

        return repoListSource.getRepoList(q)
            .map {
                Resource.fromResponse(it)
            }
    }
}