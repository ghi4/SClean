package com.project.laundryapp.core.data

import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.ApiResponse
import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.response.LaundryStatusDetail
import com.project.laundryapp.core.data.remote.response.LaundryStatusResponse
import com.project.laundryapp.core.data.remote.response.UserStatusResponse
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

    fun getLaundryList(): Flow<Resource<LaundryStatusResponse>> {
        return object : RemoteResource<LaundryStatusResponse, LaundryStatusResponse>() {
            override fun createCall(): Flow<ApiResponse<LaundryStatusResponse>> {
                return remoteDataSource.getLaundryList()
            }

            override fun convertCallResult(data: LaundryStatusResponse): Flow<LaundryStatusResponse> {
                return flow { emit(data) }
            }
        }.asFlow()
    }

    fun getLaundryDetail(laundryId: String): Flow<Resource<LaundryStatusDetail>> {
        return object : RemoteResource<LaundryStatusDetail, LaundryStatusDetail>() {
            override fun createCall(): Flow<ApiResponse<LaundryStatusDetail>> {
                return remoteDataSource.getLaundryDetail(laundryId)
            }

            override fun convertCallResult(data: LaundryStatusDetail): Flow<LaundryStatusDetail> {
                return flow { emit(data) }
            }
        }.asFlow()
    }

}