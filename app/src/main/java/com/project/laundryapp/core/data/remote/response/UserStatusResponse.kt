package com.project.laundryapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserStatusResponse(
    @SerializedName("message")
    var message: String,

    @SerializedName("data")
    var userData: UserDataResponse,

    @SerializedName("error")
    var error: String
)
