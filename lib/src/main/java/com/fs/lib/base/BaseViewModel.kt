package com.fs.lib.base

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : IViewModel, ViewModel() {


    val compositeDisposable = CompositeDisposable()

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate() {
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }
}
