package com.fs.lib.base

import android.app.Application

open class BaseApplication : Application() {




    open fun init() {

    }

    override fun onCreate() {
        super.onCreate()
        init()
    }
}