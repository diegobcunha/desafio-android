package com.picpay.desafio.android.model.retrofit

import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.picpay.desafio.android.model.retrofit.exceptions.StringResourceException
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.io.Reader
import java.util.regex.Pattern

@Keep
class RestApiException(
    private val httpCode: Int,
    val errorResponse: ErrorResponse? = null,
    cause: Throwable?
) : StringResourceException(errorResponse?.message, cause = cause) {

    constructor(httpException: HttpException, gson: Gson) : this(
        httpException.code(),
        httpException.response()?.errorBody()?.serializeErrorBody(gson),
        httpException
    )
}

@Keep
data class ErrorResponse(
    val message: String? = null,
    val custom: JsonObject? = null
) {

    fun getFieldError(): String? {
        val msg = message
        return if (msg != null) {
            val pattern =
                Pattern.compile("(field_)([\\w\\d]*)(_error_)")
            val matcher = pattern.matcher(msg)
            if (matcher.find() && matcher.groupCount() == 3) {
                matcher.group(2)
            } else {
                null
            }
        } else {
            null
        }
    }
}

fun ResponseBody.serializeErrorBody(gson: Gson): ErrorResponse? {
    return try {
        charStream().fromJson<ErrorResponse>(gson)
    } catch (e: JsonSyntaxException) {
        ErrorResponse()
    } catch (e: IOException) {
        ErrorResponse()
    }
}

inline fun <reified T> Reader.fromJson(gson: Gson): T? = gson.fromJson(this, T::class.java)
