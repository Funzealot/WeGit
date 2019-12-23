package com.fs.lib.rx

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxUtil {

    companion object {
        fun <T> mainIO(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                return@ObservableTransformer it.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}
