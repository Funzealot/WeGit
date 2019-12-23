package com.fs.wegit.repository

import com.fs.lib.state.Resource
import com.fs.wegit.model.FileItemEntity
import com.fs.wegit.source.FileListSource
import io.reactivex.Observable

class FileListRepo(private val source: FileListSource) {

    fun getFileList(url: String): Observable<Resource<List<FileItemEntity>>> {

        return source.getFileList(url)
            .map {
                Resource.fromResponse(it)
            }
    }
}