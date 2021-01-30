package com.project.laundryapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.response.UserResponse

class RegisterViewModel(private val remoteDataSource: RemoteDataSource): ViewModel() {

    private val registerUser = MutableLiveData<UserResponse>()

    var userData = registerUser.switchMap {
        remoteDataSource.postRegister(
            UserResponse(
                email = it.email,
                namaLengkap = it.namaLengkap,
                nomorHp = it.nomorHp,
                password = it.password,
            )
        ).asLiveData()
    }

    fun registerUser(userResponse: UserResponse) {
        registerUser.postValue(userResponse)
    }
}