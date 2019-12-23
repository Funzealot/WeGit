package com.fs.lib.state

import retrofit2.Response

class Resource<T>(val data: T?, val status: Status, val message: String?) {


    companion object {


        fun <T> success(data: T?): Resource<T> = Resource(data, Status.SUCCESS, null)
        fun <T> loading(data: T?): Resource<T> = Resource(data, status = Status.LOADING, message = null)
        fun <T> error(msg: String): Resource<T> = Resource(null, Status.ERROR, msg)
        fun <T> init(data: T?): Resource<T> = Resource(data, Status.INIT, message = null)

        fun <T> fromResponse(response: Response<T>): Resource<T> {

            val rawRes = response.raw()
            val code = rawRes.code()
            return if (code == 200)
                success(response.body())
            else error(rawRes.message())

        }

    }

}
