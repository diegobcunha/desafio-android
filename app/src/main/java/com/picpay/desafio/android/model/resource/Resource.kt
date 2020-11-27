package com.picpay.desafio.android.model.resource

sealed class Resource<T>(open val data: T?) {

    companion object {
        fun <T> success(data: T? = null) = Success<T>(data)

        fun <T> error(throwable: Throwable? = null) = Error<T>(throwable)

        fun <T> loading(data: T? = null) = Loading<T>(data)
    }

    fun isLoading() = this is Loading

    fun isSuccess() = this is Success

    fun isError() = this is Error

    fun isNotNullSuccess() = this is Success && data != null

    fun getOrNull() = data

    fun getThrowableOrNull() = (this as? Error)?.throwable


    inline fun <R> map(mapBlock: (T?) -> R?): Resource<R> {
        return when (this) {
            is Error -> error(throwable)
            is Loading -> loading()
            is Success -> {
                val r = mapBlock(data)
                success(r)
            }
        }
    }

    inline fun <R> mapResource(mapBlock: (T?) -> Resource<R>): Resource<R> {
        return when (this) {
            is Error -> error(throwable)
            is Loading -> loading()
            is Success -> {
                mapBlock(data)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> cast(): Resource<R> {
        return map { it as? R? }
    }

    inline fun onLoading(block: (() -> Unit)): Resource<T> {
        if (this is Loading) {
            block.invoke()
        }
        return this
    }

    inline fun onSuccess(block: (T?) -> Unit): Resource<T> {
        if (isSuccess()) {
            block(getOrNull())
        }
        return this
    }

    inline fun onError(block: (Throwable?) -> Unit): Resource<T> {
        if (isError()) {
            block(getThrowableOrNull())
        }
        return this
    }

    data class Success<T>(override val data: T?) : Resource<T>(data)
    data class Error<T>(val throwable: Throwable?, override val data: T? = null) :
        Resource<T>(data) {
        fun <R> error() = error<R>(getThrowableOrNull())
    }

    data class Loading<T>(override val data: T? = null) : Resource<T>(data)
}

fun <T> loading(data: T? = null): Resource<T> =
    Resource.loading(data)
