package com.project.laundryapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("id_user")
    var id: String? = "Unknown",

    @SerializedName("email")
    var email: String? = "Unknown",

    @SerializedName("nama_lengkap")
    var namaLengkap: String? = "Unknown",

    @SerializedName("nomor_hp")
    var nomorHp: String? = "Unknown",

    @SerializedName("photo")
    var photo: String? = "Unknown",

    @SerializedName("password")
    var password: String? = "Unknown",

    @SerializedName("alamat")
    var alamat: String? = "Unknown",

    @SerializedName("kota")
    var kota: String? = "Unknown",

    @SerializedName("kecamatan")
    var kecamatan: String? = "Unknown",

    @SerializedName("kelurahan")
    var kelurahan: String? = "Unknown",

    @SerializedName("kode_pos")
    var kodePos: String? = "Unknown",

    @SerializedName("keterangan_alamat")
    var keteranganAlamat: String? = "Unknown"
)
