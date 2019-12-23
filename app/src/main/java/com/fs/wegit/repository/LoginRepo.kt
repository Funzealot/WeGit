package com.fs.wegit.repository

import com.fs.lib.state.Resource
import com.fs.wegit.model.AuthEntity
import com.fs.wegit.source.LoginSource
import io.reactivex.Observable

class LoginRepo(private val source: LoginSource) {


    fun auth(userName: String, password: String): Observable<Resource<AuthEntity>> {

        return source.auth(userName, password)
            .map {
                it
            }
    }
}
