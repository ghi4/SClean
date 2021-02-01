package com.project.laundryapp.core.data.remote.response.laundry

import com.google.gson.annotations.SerializedName

data class LaundryServiceResponse(
    @SerializedName("id")
     val idLayanan: String? = "Unknown",

    @SerializedName("id_laundry")
     val idLaundry: String? = "Unknown",

    @SerializedName("nama_layanan")
     val namaLayanan: String? = "Unknown",

    @SerializedName("daftar_layanan")
    val daftarLayanan: List<LaundryOrderInput>? = null,

    @SerializedName("estimasi")
     val estimasi: String? = "Unknown",

    @SerializedName("harga")
     val harga: Int? = 0,

    @SerializedName("satuan")
     val satuan: String? = "Unknown",

    @SerializedName("qty")
    var qty: Int? = 0,

    @SerializedName("create_date")
     val createDate: String? = "Unknown",

    @SerializedName("update_date")
     val updateDate: String? = "Unknown"
)
