package com.fs.lib.http.retrofit

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter
import java.nio.charset.Charset

class CustomRequestBodyConvert<T>(private val gson: Gson): Converter<T, RequestBody>{



    override fun convert(value: T): RequestBody {

        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), Charset.forName("UTF-8"))
        val jsonWriter = gson.newJsonWriter(writer)
        jsonWriter.close()
        return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), buffer.readByteString())
    }
}