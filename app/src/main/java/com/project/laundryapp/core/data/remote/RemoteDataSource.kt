package com.project.laundryapp.core.data.remote

import com.project.laundryapp.core.data.remote.response.StatusResponse
import com.project.laundryapp.core.data.remote.response.UserResponse
import com.project.laundryapp.core.data.remote.retrofit.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val retrofitService: RetrofitInterface) {
    private val noInternet = "Masalah koneksi internet."

    fun postLogin(user: UserResponse): Flow<ApiResponse<StatusResponse>> {
        return flow {
            try {
                val response = retrofitService.postLogin(
                    user.email.toString(),
                    user.password.toString(),
                )
                emit(ApiResponse.Success(response))

            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postRegister(user: UserResponse): Flow<ApiResponse<StatusResponse>> {
        return flow {
            try {
                val response = retrofitService.postRegister(
                    user.namaLengkap.toString(),
                    user.email.toString(),
                    user.password.toString(),
                    user.nomorHp.toString()
                )
                emit(ApiResponse.Success(response))

            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postAddress(user: UserResponse): Flow<ApiResponse<StatusResponse>> {
        return flow {
            try {
                val response = retrofitService.postAddress(
                    user.id.toString(),
                    user.alamat.toString(),
                    user.kota.toString(),
                    user.kecamatan.toString(),
                    user.kelurahan.toString(),
                    user.kodePos.toString(),
                    user.keteranganAlamat.toString()
                )
                emit(ApiResponse.Success(response))

            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }
}