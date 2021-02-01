package com.project.laundryapp.core.data.remote.response.laundry

import com.google.gson.annotations.SerializedName

data class LaundryHistoryResponse(
    @SerializedName("id_pesanan")
    val idPesanan:String,

    @SerializedName("nomor_pesanan")
    val nomorPesanan:String,

    @SerializedName("id_user")
    val idUser:String,

    @SerializedName("id_laundry")
    val idLaundry:String,

    @SerializedName("tgl_pesan")
    val tglPesan:String,

    @SerializedName("total")
    val total:String,

    @SerializedName("status")
    val status:String,

    @SerializedName("tgl_bayar")
    val tglBayar:Any,

    @SerializedName("tgl_status")
    val tglStatus:Any,

    @SerializedName("tgl_pengantaran")
    val tglPengantaran:Any,

    @SerializedName("catatan_order")
    val catatanOrder:Any,

    @SerializedName("nama_laundry")
    val namaLaundry:String,

    @SerializedName("alamat")
    val alamat:String,

    @SerializedName("jam_buka")
    val jamBuka:String,

    @SerializedName("jam_tutup")
    val jamTutup:String,

    @SerializedName("nomor_telepon")
    val nomorTelepon:String,

    @SerializedName("daftar_layanan")
    val daftarLayanan: List<LaundryServiceResponse>? = null
)
