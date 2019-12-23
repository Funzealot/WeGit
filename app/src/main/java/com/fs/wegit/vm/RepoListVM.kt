package com.fs.wegit.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fs.lib.base.BaseViewModel
import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.lib.http.retrofit.UserEntity
import com.fs.lib.rx.RxUtil
import com.fs.lib.state.Resource
import com.fs.wegit.App
import com.fs.wegit.model.AuthUserEntity
import com.fs.wegit.model.RepoListEntity
import com.fs.wegit.repository.RepoListRepo
import com.fs.wegit.service.GithubService

class RepoListVM(private val repoListRepo: RepoListRepo) : BaseViewModel() {

    val TAG = this::class.java.simpleName

    val repoLiveData = MutableLiveData<Resource<RepoListEntity>>()
    val userLiveData = MutableLiveData<Resource<AuthUserEntity>>()


    fun loadRepoList(q: String) {
        repoLiveData.value = Resource.loading(null)
        val disposable = repoListRepo.getRepoList(q)
            .subscribe({
                repoLiveData.value = it
            }, {
                Log.e("FUN", it.message!!)
            })

        compositeDisposable.add(disposable)
    }

    fun loadUser() {

        val disposable = RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .getAuthUser("token ${App.TOKEN}")
            .compose(RxUtil.mainIO())
            .subscribe({
                if (it.code() == 200) {
                    Log.d("FUN", "成功 --------  ${it.body()?.login}")
                    userLiveData.value = Resource.success(it.body())
                }
            }, {
                Log.d("FUN", "loadUser ------ ${it.message}")
            })

        compositeDisposable.add(disposable)
    }


    class ViewModelFactory(private val repoListRepo: RepoListRepo) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T = RepoListVM(repoListRepo) as T

    }
}