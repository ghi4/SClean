package com.project.laundryapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("title")
    val title: String,

    @SerializedName("message")
    val message: String
)
