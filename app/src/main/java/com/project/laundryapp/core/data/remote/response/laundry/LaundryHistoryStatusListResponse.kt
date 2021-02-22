package com.project.laundryapp.core.data.remote.response.laundry

import com.google.gson.annotations.SerializedName

data class LaundryHistoryStatusListResponse(
    @SerializedName("message")
    var message: String? = "Unknown",

    @SerializedName("data")
    var data: List<LaundryHistoryResponse>? = null,

    @SerializedName("error")
    var error: String? = "Unknown"
)
