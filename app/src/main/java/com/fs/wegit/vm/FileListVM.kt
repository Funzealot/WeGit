package com.fs.wegit.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fs.lib.base.BaseViewModel
import com.fs.lib.state.Resource
import com.fs.wegit.model.FileItemEntity
import com.fs.wegit.repository.FileListRepo

class FileListVM(private val repo: FileListRepo) : BaseViewModel() {

    val fileListLiveData = MutableLiveData<Resource<List<FileItemEntity>>>()

    fun getFileList(url: String) {
        fileListLiveData.value = Resource.loading(null)
        val dispose = repo.getFileList(url)
            .subscribe({
                fileListLiveData.value = it
            }, {
                Log.e("FUN", "出错了 ${it.printStackTrace()}")
            })

        compositeDisposable.add(dispose)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    class ViewModelFactory(private val repo: FileListRepo) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T = FileListVM(repo) as T

    }
}