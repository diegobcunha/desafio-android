package com.picpay.desafio.android.model.retrofit

import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.model.resource.Resource
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    suspend fun getUsers(): Resource<List<User>>
}