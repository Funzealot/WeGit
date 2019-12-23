package com.fs.wegit

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.fs.lib.base.BaseApplication
import com.fs.wegit.config.Config

class App : BaseApplication() {




    companion object {
        lateinit var instance: Application
        var TOKEN = "11f06fc97fbb5ec74bed3e6fd99e374f28b43b1b"
    }

    override fun init() {
        Config.initRetrofitManager()
        instance = this
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}