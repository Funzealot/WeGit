package com.fs.wegit.helper

import android.content.Context
import android.widget.ImageView
import com.fs.wegit.config.GlideApp

object GlideHelper {

    fun loadToView(context: Context, imgUrl: String, imageView: ImageView) {

        GlideApp.with(context)
            .load(imgUrl)

            .into(imageView)
    }
}