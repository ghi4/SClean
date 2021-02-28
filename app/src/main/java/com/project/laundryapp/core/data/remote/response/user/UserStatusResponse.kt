package com.project.laundryapp.core.data.remote.response.user

import com.google.gson.annotations.SerializedName
import com.project.laundryapp.core.data.remote.response.ErrorResponse

data class UserStatusResponse(
    @SerializedName("message")
    var message: String,

    @SerializedName("data")
    var userData: UserDataResponse,

    @SerializedName("error")
    var error: String
)
