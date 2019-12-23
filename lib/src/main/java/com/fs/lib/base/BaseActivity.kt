package com.fs.lib.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun setLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        init()

    }

    open fun init() {
        initView()
        initData()
    }

    open fun initView() {}
    open fun initData() {}

    fun getContentView(): ViewGroup {

        val rootView = findViewById<FrameLayout>(android.R.id.content)
        return rootView[0] as ViewGroup
    }

}
