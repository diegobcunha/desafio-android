package com.picpay.desafio.android.model.resource

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class ResourceObserver<T>(
    private val lifecycleOwner: LifecycleOwner,
    private var loading: (() -> Unit)? = null,
    private var success: ((T) -> Unit)? = null,
    private var error: ((Throwable?) -> Unit)? = null,
    private var terminate: ((Resource<T>?) -> Unit?)? = null
) : Observer<Resource<T>> {

    var liveData: LiveData<Resource<T>>? = null

    override fun onChanged(t: Resource<T>?) {
        when (t) {
            is Resource.Loading -> {
                loading?.invoke()
            }
            is Resource.Success -> {
                if (t.data == null) {
                    error?.invoke(Exception())
                } else {
                    success?.invoke(t.data ?: return)
                }
                terminate?.invoke(t)
            }
            is Resource.Error -> {
                error?.invoke(t.throwable)
                terminate?.invoke(t)
            }
        }
    }

    fun onLoading(block: () -> Unit): ResourceObserver<T> {
        loading = block
        return this
    }

    fun onSuccess(block: (T?) -> Unit): ResourceObserver<T> {
        success = block
        return this
    }

    fun onError(block: (Throwable?) -> Unit): ResourceObserver<T> {
        error = block
        return this
    }

    fun onTerminate(block: (Resource<T>?) -> Unit): ResourceObserver<T> {
        terminate = block
        return this
    }

    fun observe() {
        liveData?.observe(lifecycleOwner, this)
    }
}
