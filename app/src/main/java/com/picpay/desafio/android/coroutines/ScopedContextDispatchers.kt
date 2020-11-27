package com.picpay.desafio.android.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ScopedContextDispatchers(val scope: CoroutineScope, private val dispatcher: DispatchersProvider) {

    val main: CoroutineContext
        get() = scope.coroutineContext + dispatcher.main

    val io: CoroutineContext
        get() = scope.coroutineContext + dispatcher.io



}