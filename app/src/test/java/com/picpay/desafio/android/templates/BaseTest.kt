package com.picpay.desafio.android.templates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.coroutines.MainCoroutineTestRule
import org.junit.Rule

abstract class BaseTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineTestRule()

    val testDispatchersProvider by lazy {
        coroutineTestRule.dispatchersProvider
    }
}