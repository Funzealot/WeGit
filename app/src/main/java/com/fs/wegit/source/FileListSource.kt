package com.fs.wegit.source

import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.lib.rx.RxUtil
import com.fs.wegit.model.FileItemEntity
import com.fs.wegit.service.GithubService
import io.reactivex.Observable
import retrofit2.Response

class FileListSource {

    fun getFileList(url: String): Observable<Response<List<FileItemEntity>>> {

        return RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .getFileList(url)
            .compose(RxUtil.mainIO())
    }
}