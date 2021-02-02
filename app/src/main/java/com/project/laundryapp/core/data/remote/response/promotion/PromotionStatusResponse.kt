package com.project.laundryapp.core.data.remote.response.promotion

import com.google.gson.annotations.SerializedName
import com.project.laundryapp.core.data.remote.response.ErrorResponse
import com.project.laundryapp.core.data.remote.response.user.UserDataResponse

data class PromotionStatusResponse(
        @SerializedName("message")
        var message: String,

        @SerializedName("data")
        var data: List<PromotionResponse>? = null,

        @SerializedName("error")
        var error: ErrorResponse
)
