package com.picpay.desafio.android.templates

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.picpay.desafio.android.coroutines.DispatchersProvider
import com.picpay.desafio.android.model.resource.Resource
import com.picpay.desafio.android.model.resource.loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class SuspendFetchViewModel<T>(dispatcher: DispatchersProvider) :
    ResourceViewModel<T>(dispatcher) {

    protected open var lastRequestedValue: T? = null

    override fun loadContent(): LiveData<Resource<T>> {
        return createLiveData()
    }

    protected abstract suspend fun fetch(): Resource<T>


    private fun createLiveData() = liveData<Resource<T>> {
        emit(loading())
        try {
            val resource = withContextFetch()
            if (!resource.isSuccess()) {
                emit(resource.cast())
            } else {
                emit(resource)
            }
        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }

    private suspend fun withContextFetch() = withContext(io()) {
        fetch().onSuccess {
            lastRequestedValue = it
        }
    }
}