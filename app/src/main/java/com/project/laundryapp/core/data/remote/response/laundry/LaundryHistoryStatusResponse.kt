package com.project.laundryapp.core.data.remote.response.laundry

import com.google.gson.annotations.SerializedName

data class LaundryHistoryStatusResponse(
        @SerializedName("message")
        var message: String? = "Unknown",

        @SerializedName("data")
        var data: LaundryHistoryResponse? = null,

        @SerializedName("error")
        var error: String? = "Unknown"
)
