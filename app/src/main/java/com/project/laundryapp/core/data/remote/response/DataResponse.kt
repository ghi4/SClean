package com.project.laundryapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("user")
    var user: UserResponse,

    @SerializedName("nama_lengkap")
     val namaLengkap:String,

    @SerializedName("email")
     val email:String,

    @SerializedName("password")
     val password:String,

    @SerializedName("nomor_hp")
     val nomorHp:String,

    @SerializedName("create_date")
     val createDate:String,

    @SerializedName("photo")
    var photo: String,

    @SerializedName("alamat")
     val alamat:String,

    @SerializedName("kota")
     val kota:String,

    @SerializedName("kecamatan")
     val kecamatan:String,

    @SerializedName("kelurahan")
     val kelurahan:String,

    @SerializedName("kode_pos")
     val kodePos:String,

    @SerializedName("keterangan_alamat")
     val keteranganAlamat:String,

    @SerializedName("id_user")
     val idUser:String
)
