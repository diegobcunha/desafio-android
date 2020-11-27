package com.picpay.desafio.android.injection

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.model.retrofit.PicPayService
import com.picpay.desafio.android.model.retrofit.ResourceCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val restModule = module {
    single { createEndpoint(PicPayService::class.java) }

}

private fun <T> Scope.createEndpoint(clazz: Class<T>): T {
    val url = "http://careers.picpay.com/tests/mobdev/"
    val interceptor = HttpLoggingInterceptor()
    interceptor.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val gson = GsonBuilder().create()
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(ResourceCallAdapterFactory(gson))
        .build()

    return retrofit.create(clazz)
}

