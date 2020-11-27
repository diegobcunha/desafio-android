package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.injection.coroutineModule
import com.picpay.desafio.android.injection.injectionModule
import com.picpay.desafio.android.injection.restModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(injectionModule, restModule, coroutineModule))
            androidContext(this@PicPayApplication)
        }
    }
}