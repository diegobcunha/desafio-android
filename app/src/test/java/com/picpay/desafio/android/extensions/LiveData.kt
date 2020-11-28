package com.picpay.desafio.android.extensions

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.model.resource.Resource
import org.junit.Assert

fun <T> assertLiveDataEquals(
    expected: T?,
    liveData: LiveData<T>,
    doAfterSubscribe: (() -> Unit)? = null
) {
    var value: T? = null
    liveData.observeForever { value = it }
    doAfterSubscribe?.invoke()
    Assert.assertEquals(expected, value)
}

fun <T> LiveData<Resource<T>>.assertLiveDataEquals(
    expected: T?,
    doAfterSubscribe: (() -> Unit)? = null
) {
    var value: T? = null
    observeForever { value = it.data }
    doAfterSubscribe?.invoke()
    Assert.assertEquals(expected, value)
}

fun <T> LiveData<Resource<T>>.assertLiveDataError(doAfterSubscribe: (() -> Unit)? = null) {
    var value: Resource<T>? = null
    observeForever { value = it }
    doAfterSubscribe?.invoke()
    Assert.assertEquals(Resource.error<T>(), value)
}
