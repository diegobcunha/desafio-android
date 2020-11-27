package com.picpay.desafio.android

import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.model.retrofit.PicPayService

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}