package com.fs.wegit.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fs.lib.base.BaseViewModel
import com.fs.lib.state.Resource
import com.fs.lib.http.retrofit.UserEntity
import com.fs.wegit.repository.UserRepo
import io.reactivex.disposables.Disposable

class UserVM(private val userRepo: UserRepo) : BaseViewModel() {

    val user = MutableLiveData<Resource<UserEntity>>()
    lateinit var disposable: Disposable
    fun getUser(userName: String) {
        disposable = userRepo.getUser(userName)
            .subscribe {
                user.postValue(it)
            }
    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(private val userRepo: UserRepo) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserVM(userRepo) as T
        }

    }


}
