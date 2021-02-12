package com.project.laundryapp.core.data.remote.response.laundry

import com.google.gson.annotations.SerializedName

data class LaundryHistoryResponse(
        @SerializedName("id_pesanan")
        val idPesanan: String? = "Unknown",

        @SerializedName("nomor_pesanan")
        val nomorPesanan: String? = "Unknown",

        @SerializedName("id_user")
        val idUser: String? = "Unknown",

        @SerializedName("id_laundry")
        val idLaundry: String? = "Unknown",

        @SerializedName("tgl_pesan")
        val tglPesan: String? = "Unknown",

        @SerializedName("biaya_pengantaran")
        val biayaPengantaran: Int? = 0,

        @SerializedName("total")
        val total: Int? = 0,

        @SerializedName("status")
        val status: String? = "Unknown",

        @SerializedName("tgl_bayar")
        val tglBayar: String? = "Unknown",

        @SerializedName("tgl_status")
        val tglStatus: String? = "Unknown",

        @SerializedName("tgl_pengantaran")
        val tglPengantaran: String? = "Unknown",

        @SerializedName("catatan_order")
        val catatanOrder: String? = "Unknown",

        @SerializedName("nama_laundry")
        val namaLaundry: String? = "Unknown",

        @SerializedName("alamat")
        val alamat: String? = "Unknown",

        @SerializedName("jam_buka")
        val jamBuka: String? = "Unknown",

        @SerializedName("jam_tutup")
        val jamTutup: String? = "Unknown",

        @SerializedName("nomor_telepon")
        val nomorTelepon: String? = "Unknown",

        @SerializedName("daftar_layanan")
        val daftarLayanan: List<LaundryServiceResponse>? = null
)
