package com.project.laundryapp.core.data

import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.ApiResponse
import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.response.laundry.*
import com.project.laundryapp.core.data.remote.response.user.UserStatusResponse
import com.project.laundryapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LaundryRepository(private val remoteDataSource: RemoteDataSource) {

    fun postLogin(user: User): Flow<Resource<User>> {
        return object : RemoteResource<User, UserStatusResponse>() {
            override fun createCall(): Flow<ApiResponse<UserStatusResponse>> {
                return remoteDataSource.postLogin(user)
            }

            override fun convertCallResult(data: UserStatusResponse): Flow<User> {
                val result = DataMapper.remoteUserToLocal(data.userData)
                return flow { emit(result) }
            }
        }.asFlow()
    }

    fun postRegister(user: User): Flow<Resource<User>> {
        return object : RemoteResource<User, UserStatusResponse>() {
            override fun createCall(): Flow<ApiResponse<UserStatusResponse>> {
                return remoteDataSource.postRegister(user)
            }

            override fun convertCallResult(data: UserStatusResponse): Flow<User> {
                val result = DataMapper.remoteUserToLocal(data.userData)
                return flow { emit(result) }
            }
        }.asFlow()
    }

    fun postAddress(user: User): Flow<Resource<User>> {
        return object : RemoteResource<User, UserStatusResponse>() {
            override fun createCall(): Flow<ApiResponse<UserStatusResponse>> {
                return remoteDataSource.postAddress(user)
            }

            override fun convertCallResult(data: UserStatusResponse): Flow<User> {
                val result = DataMapper.remoteUserToLocal(data.userData)
                return flow { emit(result) }
            }
        }.asFlow()
    }

    fun getLaundryList(): Flow<Resource<LaundryStatusListResponse>> {
        return object : RemoteResource<LaundryStatusListResponse, LaundryStatusListResponse>() {
            override fun createCall(): Flow<ApiResponse<LaundryStatusListResponse>> {
                return remoteDataSource.getLaundryList()
            }

            override fun convertCallResult(data: LaundryStatusListResponse): Flow<LaundryStatusListResponse> {
                return flow { emit(data) }
            }
        }.asFlow()
    }

    fun getLaundryDetail(laundryId: String): Flow<Resource<LaundryStatusResponse>> {
        return object : RemoteResource<LaundryStatusResponse, LaundryStatusResponse>() {
            override fun createCall(): Flow<ApiResponse<LaundryStatusResponse>> {
                return remoteDataSource.getLaundryDetail(laundryId)
            }

            override fun convertCallResult(data: LaundryStatusResponse): Flow<LaundryStatusResponse> {
                return flow { emit(data) }
            }
        }.asFlow()
    }

    fun postOrder(idLaundry: String, idUser: String, serviceList: ArrayList<LaundryOrderInput>): Flow<Resource<LaundryStatusListResponse>> {
        return object : RemoteResource<LaundryStatusListResponse, LaundryStatusListResponse>() {
            override fun createCall(): Flow<ApiResponse<LaundryStatusListResponse>> {
                return remoteDataSource.postOrder(idLaundry, idUser, serviceList)
            }

            override fun convertCallResult(data: LaundryStatusListResponse): Flow<LaundryStatusListResponse> {
                return flow { emit(data) }
            }

        }.asFlow()
    }

    fun getLaundryHistoryByUserId(userId: String): Flow<Resource<LaundryHistoryStatusListResponse>> {
        return object : RemoteResource<LaundryHistoryStatusListResponse, LaundryHistoryStatusListResponse>() {
            override fun createCall(): Flow<ApiResponse<LaundryHistoryStatusListResponse>> {
                return remoteDataSource.getLaundryHistoryByIdUser(userId)
            }

            override fun convertCallResult(data: LaundryHistoryStatusListResponse): Flow<LaundryHistoryStatusListResponse> {
                return flow { emit(data) }
            }

        }.asFlow()
    }

    fun getLaundryHistoryDetailByHistoryId(historyId: String): Flow<Resource<LaundryHistoryStatusResponse>> {
        return object : RemoteResource<LaundryHistoryStatusResponse, LaundryHistoryStatusResponse>() {
            override fun createCall(): Flow<ApiResponse<LaundryHistoryStatusResponse>> {
                return remoteDataSource.getLaundryHistoryDetailByHistoryId(historyId)
            }

            override fun convertCallResult(data: LaundryHistoryStatusResponse): Flow<LaundryHistoryStatusResponse> {
                return flow { emit(data) }
            }

        }.asFlow()
    }

}