package com.picpay.desafio.android.model

import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.model.resource.Resource
import com.picpay.desafio.android.model.retrofit.PicPayService

class PicPayRepository(private val api: PicPayService) {

    suspend fun retrieveListUsers(): Resource<List<User>> {
        return api.getUsers()
    }
}