package com.project.laundryapp.core.data.remote

import android.util.Log
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.response.StatusResponse
import com.project.laundryapp.core.data.remote.retrofit.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val retrofitService: RetrofitInterface) {
    private val noInternet = "Masalah koneksi internet."

    fun postLogin(user: User): Flow<ApiResponse<StatusResponse>> {
        return flow {
            try {
                val response = retrofitService.postLogin(
                    user.email.toString(),
                    user.password.toString(),
                )

                Log.d("RemoteData", "" + response.error)
                Log.d("RemoteData", "" + response.message)

                if(response.error == "null") {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }

            } catch (e: Exception) {
                Log.d("RemoteData", "" + e.toString())
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postRegister(user: User): Flow<ApiResponse<StatusResponse>> {
        return flow {
            try {
                val response = retrofitService.postRegister(
                    user.namaLengkap.toString(),
                    user.email.toString(),
                    user.password.toString(),
                    user.nomorHp.toString()
                )

                Log.d("RemoteData", "" + response.error)
                Log.d("RemoteData", "" + response.message)

                emit(ApiResponse.Success(response))

            } catch (e: Exception) {
                Log.d("RemoteData", "" + e.toString())
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postAddress(user: User): Flow<ApiResponse<StatusResponse>> {
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

                Log.d("RemoteData", "" + response.error)
                Log.d("RemoteData", "" + response.message)

                emit(ApiResponse.Success(response))

            } catch (e: Exception) {
                Log.d("RemoteData", "" + e.toString())
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }
}