package com.project.laundryapp.core.data

import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.ApiResponse
import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.response.StatusResponse
import com.project.laundryapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LaundryRepository(private val remoteDataSource: RemoteDataSource) {

    fun postLogin(user: User): Flow<Resource<User>> {
        return object : RemoteResource<User, StatusResponse>() {
            override fun createCall(): Flow<ApiResponse<StatusResponse>> {
                return remoteDataSource.postLogin(user)
            }

            override fun convertCallResult(data: StatusResponse): Flow<User> {
                val result = DataMapper.remoteUserToLocal(data.data.user)
                return flow { emit(result) }
            }
        }.asFlow()
    }

    fun postRegister(user: User): Flow<Resource<User>> {
        return object : RemoteResource<User, StatusResponse>() {
            override fun createCall(): Flow<ApiResponse<StatusResponse>> {
                return remoteDataSource.postRegister(user)
            }

            override fun convertCallResult(data: StatusResponse): Flow<User> {
                val result = DataMapper.remoteUserToLocal(data.data)
                return flow { emit(result) }
            }
        }.asFlow()
    }

    fun postAddress(user: User): Flow<Resource<User>> {
        return object : RemoteResource<User, StatusResponse>() {
            override fun createCall(): Flow<ApiResponse<StatusResponse>> {
                return remoteDataSource.postAddress(user)
            }

            override fun convertCallResult(data: StatusResponse): Flow<User> {
                val result = DataMapper.remoteUserToLocal(data.data)
                return flow { emit(result) }
            }
        }.asFlow()
    }

}