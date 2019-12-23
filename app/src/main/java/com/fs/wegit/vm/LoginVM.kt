package com.fs.wegit.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fs.lib.base.BaseViewModel
import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.lib.rx.RxUtil
import com.fs.lib.state.Resource
import com.fs.wegit.App
import com.fs.wegit.model.AuthEntity
import com.fs.wegit.model.AuthUserEntity
import com.fs.wegit.repository.LoginRepo
import com.fs.wegit.service.GithubService

class LoginVM(private val repo: LoginRepo) : BaseViewModel() {

    val authLiveData = MutableLiveData<Resource<AuthEntity>>()
    val authUserLiveData = MutableLiveData<Resource<AuthUserEntity>>()

    fun auth(userName: String, password: String) {

        authLiveData.value = Resource.loading(null)
        val disposable = repo.auth(userName, password)
            .subscribe({
                authLiveData.value = it

            }, {

            })

        compositeDisposable.add(disposable)
    }


    fun getAuthUserInfo() {

        val disposable = RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .getAuthUser("token ${App.TOKEN}")
            .compose(RxUtil.mainIO())
            .subscribe({
                if (it.code() == 200) {
                    Log.d("FUN", "成功 --------  ${it.body()?.login}")
                    authUserLiveData.value = Resource.success(it.body())
                }
            }, {
                Log.d("FUN", "loadUser ------ ${it.message}")
            })

        compositeDisposable.add(disposable)
    }


    class ViewModelFactory(private val repo: LoginRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = LoginVM(repo) as T

    }
}