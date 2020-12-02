package com.picpay.desafio.android.injection

import com.picpay.desafio.android.model.PicPayRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { PicPayRepository(get()) }
}