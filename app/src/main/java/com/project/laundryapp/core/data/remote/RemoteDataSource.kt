package com.project.laundryapp.core.data.remote

import com.google.gson.Gson
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.response.laundry.*
import com.project.laundryapp.core.data.remote.response.promotion.PromotionStatusResponse
import com.project.laundryapp.core.data.remote.response.user.UserStatusResponse
import com.project.laundryapp.core.data.remote.retrofit.RetrofitInterface
import com.project.laundryapp.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val retrofitService: RetrofitInterface) {
    private val noInternet = "Masalah koneksi internet."

    fun postLogin(user: User): Flow<ApiResponse<UserStatusResponse>> {
        return flow {
            try {
                val response = retrofitService.postLogin(
                    user.email.toString(),
                    user.password.toString(),
                )

                emit(ApiResponse.Success(response))

            } catch (e: Exception) {
                emit(ApiResponse.Error(Utils.parseError(e.toString())))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postRegister(user: User): Flow<ApiResponse<UserStatusResponse>> {
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

    fun postAddress(user: User): Flow<ApiResponse<UserStatusResponse>> {
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

    fun deleteOrder(idOrder: String): Flow<ApiResponse<LaundryStatusResponse>> {
        return flow {
            try {
                val response = retrofitService.deleteOrder(idOrder)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getLaundryList(): Flow<ApiResponse<LaundryStatusListResponse>> {
        return flow {
            try {
                val response = retrofitService.getLaundryList()
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPromotionList(): Flow<ApiResponse<PromotionStatusResponse>> {
        return flow {
            try {
                val response = retrofitService.getPromotionList()
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getLaundryDetail(laundryId: String): Flow<ApiResponse<LaundryStatusResponse>> {
        return flow {
            try {
                val response = retrofitService.getLaundryDetail(laundryId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postOrder(
        idLaundry: String,
        idUser: String,
        serviceList: ArrayList<LaundryOrderInput>
    ): Flow<ApiResponse<LaundryStatusListResponse>> {
        return flow {
            try {
                val serviceListJson = Gson().toJson(serviceList.toList())
                val response = retrofitService.postOrder(idLaundry, serviceListJson, idUser)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getLaundryHistoryByIdUser(idUser: String): Flow<ApiResponse<LaundryHistoryStatusListResponse>> {
        return flow {
            try {
                val response = retrofitService.getLaundryHistoryByUserId(idUser)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getLaundryHistoryDetailByHistoryId(idHistory: String): Flow<ApiResponse<LaundryHistoryStatusResponse>> {
        return flow {
            try {
                val response = retrofitService.getLaundryHistoryDetailByHistoryId(idHistory)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(noInternet))
            }
        }.flowOn(Dispatchers.IO)
    }
}