package com.fs.wegit.repository

import com.fs.lib.state.Resource
import com.fs.lib.http.retrofit.UserEntity
import com.fs.wegit.source.UserSource
import io.reactivex.Observable

class UserRepo(private val userDataSource: UserSource) {
    

    fun getUser(userName: String): Observable<Resource<UserEntity>> {
        
        return userDataSource
            .getUser(userName)
            .flatMap { 
                Observable.just(Resource.fromResponse(it))
            }
    }

    fun getDataStatus(){
        
    }
}
