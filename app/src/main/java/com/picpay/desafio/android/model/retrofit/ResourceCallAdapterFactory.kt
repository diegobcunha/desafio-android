package com.picpay.desafio.android.model.retrofit

import com.google.gson.Gson
import com.picpay.desafio.android.model.resource.Resource
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit

class ResourceCallAdapterFactory(private val gson: Gson) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Resource::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResourceCallAdapter(resultType, gson)
                }
                else -> null
            }
        }
        else -> null
    }
}
