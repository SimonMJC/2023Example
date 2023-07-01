package com.example.wit.data.model

import com.google.gson.annotations.SerializedName

abstract class ErrorResult {
    @SerializedName("error")
    val error: ErrorData? = null
}

data class ErrorData(
    val code: Int?,
    val type: String?,
    val message: String?
)

