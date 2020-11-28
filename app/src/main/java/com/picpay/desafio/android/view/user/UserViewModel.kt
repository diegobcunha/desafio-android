package com.picpay.desafio.android.view.user

import com.picpay.desafio.android.coroutines.DispatchersProvider
import com.picpay.desafio.android.model.PicPayRepository
import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.model.resource.Resource
import com.picpay.desafio.android.templates.SuspendFetchViewModel

class UserViewModel(
    dispatchers: DispatchersProvider,
    private val api: PicPayRepository
) : SuspendFetchViewModel<List<User>>(dispatchers) {

    override suspend fun fetch(): Resource<List<User>> {
        return api.retrieveListUsers()
    }
}