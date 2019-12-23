package com.fs.lib.util

import android.content.Context
import android.util.DisplayMetrics
import android.app.Activity
import android.view.Display



/**
 * Created by Fan on 2019/3/19.
 * Fighting!!!
 */

object DisplayUtil {

    fun dp2px(context: Context, dp: Float): Int{
        val  scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun px2dp(context:  Context, px: Float): Int {
        val  scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun getWindowWidth(context: Context): Int {

        val display = (context as Activity?)?.windowManager?.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display?.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getWindowHeight(context: Context): Int {

        val display = (context as Activity).windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
