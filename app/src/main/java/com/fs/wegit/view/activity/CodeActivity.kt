package com.fs.wegit.view.activity

import android.annotation.SuppressLint
import android.util.Log
import com.fs.lib.base.BaseActivity
import com.fs.lib.http.retrofit.RetrofitManager
import com.fs.lib.rx.RxUtil
import com.fs.wegit.R
import com.fs.wegit.service.GithubService
import io.github.kbiakov.codeview.highlight.ColorTheme
import io.github.kbiakov.codeview.highlight.Font
import kotlinx.android.synthetic.main.source_code_layout.*

class CodeActivity : BaseActivity() {


    lateinit var fileUrl: String

    override fun setLayoutId() = R.layout.source_code_layout


    @SuppressLint("CheckResult")
    override fun initData() {

        fileUrl = intent.getStringExtra("file_url")!!
        RetrofitManager.instance
            .createApi(GithubService.GithubService::class.java)
            .getStringText(fileUrl)
            .compose(RxUtil.mainIO())
            .subscribe({
                code_view.setCode(it.body() ?: "")
                Log.d("FUN", "----------------" + it.body())
                with(code_view.getOptions()) {
                    this?.setFont(Font.Consolas)
                }
            }, {})
    }

}