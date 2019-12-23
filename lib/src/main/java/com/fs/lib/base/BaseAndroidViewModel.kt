package com.fs.lib.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

abstract class BaseAndroidViewModel(app: Application): AndroidViewModel(app) ,IViewModel {

    override fun <T : Application?> getApplication(): T {
        return super.getApplication<T>()
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
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
