package com.fs.wegit.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fs.lib.base.BaseViewModel
import com.fs.lib.state.Resource
import com.fs.wegit.model.RepoItemEntity
import com.fs.wegit.model.RepoPageEntity
import com.fs.wegit.repository.RepoPageRepo
import io.reactivex.Observable
import io.reactivex.functions.BiFunction


class RepoVM(private val repoPageRepo: RepoPageRepo) : BaseViewModel() {


    val repoPageLiveData = MutableLiveData<Resource<RepoPageEntity>>()

    lateinit var contentUrl: String
        private set

    fun initRepoItem(initialRepoItem: RepoItemEntity) {


        repoPageLiveData.value = Resource.init(null)

        val contentUrl = initialRepoItem.contents_url.substring(0, initialRepoItem.contents_url.lastIndexOf("/"))
        this.contentUrl = contentUrl
        val disposable = repoPageRepo.getReadme(contentUrl).zipWith(Observable.just(initialRepoItem),
            BiFunction<Resource<String>, RepoItemEntity, Resource<RepoPageEntity>> { t1, t2 ->

                val readme = t1.data?.let {
                    if (it.isNotEmpty())
                        it
                    else "这个人很懒什么都没有写~"
                } ?: "这个人很懒什么都没有写~"


                Resource.success(RepoPageEntity(t2, readme))
            })

            .subscribe({
                       repoPageLiveData.value = it
            }, {
                Log.d("FUN", "出错了......")
                Log.d("FUN", it.message!!)
            })

        compositeDisposable.add(disposable)
    }

    fun getRepoItem(repoUrl: String) {

        repoPageRepo.getRepoItem(repoUrl)
    }


    class ViewModelFactory(private val repoPageRepo: RepoPageRepo) : ViewModelProvider.Factory {


        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepoVM(repoPageRepo) as T
        }

    }
}