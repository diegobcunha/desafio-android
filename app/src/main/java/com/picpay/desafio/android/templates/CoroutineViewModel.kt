package com.picpay.desafio.android.templates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.coroutines.DispatchersProvider
import com.picpay.desafio.android.coroutines.ScopedContextDispatchers
import com.picpay.desafio.android.model.resource.Resource
import com.picpay.desafio.android.model.resource.loading
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class CoroutineViewModel(protected val dispatcher: DispatchersProvider) : ViewModel() {

    protected val scopedContextProvider = ScopedContextDispatchers(viewModelScope, dispatcher)

    fun io() = scopedContextProvider.io

    fun main() = scopedContextProvider.main

    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context, start, block)

    fun launchIO(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = launch(io(), start, block)

    fun launchMain(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = launch(main(), start, block)
}

fun <T> CoroutineViewModel.createFetchLiveData(fetchBlock: suspend () -> Resource<T>) =
    liveData(main()) {
        emit(loading())
        emit(fetchResource(fetchBlock))
    }

private suspend fun <T> CoroutineViewModel.fetchResource(
    fetchBlock: suspend () -> Resource<T>
): Resource<T> {
    return try {
        val resource = withContext(io()) { fetchBlock() }
        withContext(main()) { resource }
    } catch (t: Throwable) {
        Resource.error(t)
    }
}