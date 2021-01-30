package com.project.laundryapp.core.data

import com.project.laundryapp.core.data.remote.ApiResponse
import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.response.StatusResponse
import com.project.laundryapp.core.data.remote.response.UserResponse
import kotlinx.coroutines.flow.Flow

class LaundryRepository(private val remoteDataSource: RemoteDataSource) {

    fun postRegister(user: UserResponse): Flow<ApiResponse<StatusResponse>>{
        return remoteDataSource.postRegister(user)
    }
}