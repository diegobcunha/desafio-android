package com.picpay.desafio.android.templates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.picpay.desafio.android.coroutines.DispatchersProvider
import com.picpay.desafio.android.livedata.fetchValue
import com.picpay.desafio.android.model.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class ResourceViewModel<T>(dispatcher: DispatchersProvider): CoroutineViewModel(dispatcher) {

    protected open val _resourceLiveData: MediatorLiveData<Resource<T>> by lazy {
        MediatorLiveData<Resource<T>>().apply {
            fetchValue(loadContent())
        }
    }

    val resourceLiveData: LiveData<Resource<T>> by lazy { _resourceLiveData }

    open fun tryAgain() {
        forceLoad()
    }

    open fun forceLoad() {
        _resourceLiveData.fetchValue(loadContent())
    }

    abstract fun loadContent(): LiveData<Resource<T>>


}