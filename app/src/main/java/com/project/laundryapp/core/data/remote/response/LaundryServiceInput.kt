package com.project.laundryapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class LaundryServiceInput(
    @SerializedName("id_layanan")
    var idLayanan: String,

    @SerializedName("qty")
    var jumlah: Int? = 0,

    @SerializedName("harga")
    var harga: Int? = 0
)
