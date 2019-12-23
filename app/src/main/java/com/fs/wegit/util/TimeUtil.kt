package com.fs.wegit.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {


    fun utc2cst(utc: String): Calendar{


        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA).parse(utc)!!
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8)
        return calendar
    }
}