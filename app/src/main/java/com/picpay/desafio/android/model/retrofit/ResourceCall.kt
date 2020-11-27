package com.picpay.desafio.android.model.retrofit


import com.google.gson.Gson
import com.picpay.desafio.android.model.resource.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ResourceCall<T>(proxy: Call<T>, private val gson: Gson) :
    CallDelegate<T, Resource<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<Resource<T>>) = proxy.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            val result = if (code in 200 until 300) {
                val body = response.body()
                Resource.success<T>(body)
            } else {
                val error = response.errorBody()?.serializeErrorBody(gson)
                Resource.error<T>(RestApiException(code, error, null))
            }

            callback.onResponse(this@ResourceCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = if (t is HttpException) {
                Resource.error<T>(RestApiException(t, gson))
            } else {
                Resource.error(t)
            }

            callback.onResponse(this@ResourceCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResourceCall(proxy.clone(), gson)
}
