package com.picpay.desafio.android.extensions.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.picpay.desafio.android.model.resource.Resource
import com.picpay.desafio.android.model.resource.ResourceObserver


fun <T> MediatorLiveData<T>.fetchValue(source: LiveData<T>) {
    addSource(source) {
        value = it
    }
}

fun <T> LiveData<Resource<T>>.whitOwner(lifecycleOwner: LifecycleOwner): ResourceObserver<T> {
    val observer = ResourceObserver<T>(lifecycleOwner)
    observer.liveData = this
    return observer
}