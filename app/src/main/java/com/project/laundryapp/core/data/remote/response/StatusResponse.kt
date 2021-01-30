package com.project.laundryapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("message")
    var message: String,

    @SerializedName("data")
    var data: DataResponse,

    @SerializedName("error")
    var error: String
)
