package com.project.laundryapp.core.data.remote.response.laundry

import com.google.gson.annotations.SerializedName

data class LaundryDataResponse(
        @SerializedName("id_laundry")
        val idLaundry: String? = "Unknown",

        @SerializedName("nama_laundry")
        val namaLaundry: String? = "Unknown",

        @SerializedName("nomor_telepon")
        val nomorTelepon: String? = "Unknown",

        @SerializedName("biaya_pengantaran")
        val biayaPengantaran: Int? = 0,

        @SerializedName("jam_buka")
        val jamBuka: String? = "Unknown",

        @SerializedName("jam_tutup")
        val jamTutup: String? = "Unknown",

        @SerializedName("alamat")
        val alamat: String? = "Unknown",

        @SerializedName("deskripsi")
        val deskripsi: String? = "Unknown",

        @SerializedName("photo")
        val photo: String? = "Unknown",

        @SerializedName("create_date")
        val createDate: String? = "Unknown",

        @SerializedName("update_date")
        val updateDate: String? = "Unknown",

        @SerializedName("daftar_layanan")
        val laundryService: List<LaundryServiceResponse>? = null
)