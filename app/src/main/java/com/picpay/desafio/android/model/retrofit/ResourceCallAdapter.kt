package com.picpay.desafio.android.model.retrofit

import com.google.gson.Gson
import com.picpay.desafio.android.model.resource.Resource
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

class ResourceCallAdapter(private val type: Type, private val gson: Gson) :
    CallAdapter<Type, Call<Resource<Type>>> {
    override fun adapt(call: Call<Type>): Call<Resource<Type>> = ResourceCall(call, gson)
    override fun responseType(): Type = type
}
