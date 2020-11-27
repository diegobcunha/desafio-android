package com.picpay.desafio.android.injection

import com.picpay.desafio.android.view.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val injectionModule = module {


    viewModel {
        UserViewModel(get(), get())
    }
}