package com.project.laundryapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class LaundryServiceResponse(
    @SerializedName("id")
     val id: String? = "Unknown",

    @SerializedName("id_laundry")
     val idLaundry: String? = "Unknown",

    @SerializedName("nama_layanan")
     val namaLayanan: String? = "Unknown",

    @SerializedName("estimasi")
     val estimasi: String? = "Unknown",

    @SerializedName("harga")
     val harga: String? = "Unknown",

    @SerializedName("satuan")
     val satuan: String? = "Unknown",

    @SerializedName("create_date")
     val createDate: String? = "Unknown",

    @SerializedName("update_date")
     val updateDate: String? = "Unknown"
)
