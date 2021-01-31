package com.project.laundryapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class LaundryStatusDetail(
    @SerializedName("message")
    var message: String? = "Unknown",

    @SerializedName("data")
    var data: LaundryDataResponse? = null,

    @SerializedName("error")
    var error: String? = "Unknown"
)