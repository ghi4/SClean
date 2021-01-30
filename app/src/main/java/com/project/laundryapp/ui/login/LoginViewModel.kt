package com.project.laundryapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.response.UserResponse

class LoginViewModel(private val remoteDataSource: RemoteDataSource): ViewModel() {

    private val loginUser = MutableLiveData<UserResponse>()

    var userData = loginUser.switchMap {
        remoteDataSource.postLogin(
            UserResponse(
                email = it.email,
                password = it.password,
            )
        ).asLiveData()
    }

    fun loginUser(userResponse: UserResponse) {
        loginUser.postValue(userResponse)
    }
}