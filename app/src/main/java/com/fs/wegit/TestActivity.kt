package com.fs.wegit

import android.os.Build
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import io.github.kbiakov.codeview.highlight.ColorTheme
import io.github.kbiakov.codeview.highlight.Font
import kotlinx.android.synthetic.main.test.*
import okhttp3.OkHttpClient

import okhttp3.*
import java.io.IOException

class TestActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)
    }
}