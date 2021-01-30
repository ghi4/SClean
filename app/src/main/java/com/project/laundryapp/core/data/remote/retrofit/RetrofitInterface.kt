package com.project.laundryapp.core.data.remote.retrofit

import com.project.laundryapp.core.data.remote.response.StatusResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitInterface {

    @POST("login")
    @FormUrlEncoded
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): StatusResponse

    @POST("register")
    @FormUrlEncoded
    suspend fun postRegister(
        @Field("nama_lengkap") namaLengkap: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nomor_hp") nomorHp: String
    ): StatusResponse

    @POST("update_alamat")
    @FormUrlEncoded
    suspend fun postAddress(
        @Field("id_user") idUser: String,
        @Field("alamat") namaLengkap: String,
        @Field("kota") email: String,
        @Field("kecamatan") password: String,
        @Field("kelurahan") nomorHp: String,
        @Field("kode_pos") kodePos: String,
        @Field("keterangan_alamat") keteranganAlamat: String
    ): StatusResponse
}