package com.project.laundryapp.core.data.remote.retrofit

import com.project.laundryapp.core.data.remote.response.*
import retrofit2.http.*

interface RetrofitInterface {

    @POST("login")
    @FormUrlEncoded
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): UserStatusResponse

    @POST("register")
    @FormUrlEncoded
    suspend fun postRegister(
        @Field("nama_lengkap") namaLengkap: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nomor_hp") nomorHp: String
    ): UserStatusResponse

    @POST("update_alamat")
    @FormUrlEncoded
    suspend fun postAddress(
        @Field("id_user") idUser: String,
        @Field("alamat") alamatLengkap: String,
        @Field("kota") email: String,
        @Field("kecamatan") password: String,
        @Field("kelurahan") nomorHp: String,
        @Field("kode_pos") kodePos: String,
        @Field("keterangan_alamat") keteranganAlamat: String
    ): UserStatusResponse

    @POST("input_pesanan")
    @FormUrlEncoded
    suspend fun postOrder(
        @Field("id_laundry") idLaundry: String,
        @Field("id_user") idUser: String,
        @Field("daftar_layanan") laundryService: LaundryServiceInput
    ): LaundryStatusResponse

    @GET("laundry_all")
    suspend fun getLaundryList(): LaundryStatusResponse

    @GET("laundry_detail")
    suspend fun getLaundryDetail(
        @Query("id") idLaundry: String
    ): LaundryStatusDetail
}