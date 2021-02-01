package com.project.laundryapp.core.data.remote.response.laundry

import com.google.gson.annotations.SerializedName
import com.project.laundryapp.core.data.remote.response.ErrorResponse
import com.project.laundryapp.core.data.remote.response.laundry.LaundryDataResponse

data class LaundryStatusListResponse(
    @SerializedName("message")
    var message: String? = "Unknown",

    @SerializedName("data")
    var data: List<LaundryDataResponse>? = null,

    @SerializedName("error")
    var error: ErrorResponse? = null
)