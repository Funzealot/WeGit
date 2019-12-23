package com.fs.lib.http.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomConvertFactory private constructor(val gson: Gson) : Converter.Factory() {


    companion object {
        fun create(): CustomConvertFactory {

            return create(Gson())
        }

        fun create(gson: Gson): CustomConvertFactory {

            return CustomConvertFactory(gson)
        }

    }


    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {

        val adapter = gson.getAdapter(TypeToken.get(type))
        return CustomResponseBodyConvert(gson, adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        // 获取该类的类型适配器
//        val adapter = gson.getAdapter()
        return CustomRequestBodyConvert<Type>(gson)

    }


}