package com.picpay.desafio.android.injection

import com.picpay.desafio.android.coroutines.DispatchersProvider
import com.picpay.desafio.android.coroutines.MainDispatchersProvider
import org.koin.dsl.module

val coroutineModule = module {
    factory<DispatchersProvider> { MainDispatchersProvider() }
}