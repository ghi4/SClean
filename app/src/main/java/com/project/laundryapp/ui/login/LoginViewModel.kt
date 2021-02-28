package com.project.laundryapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.LaundryRepository
import com.project.laundryapp.core.data.local.User

class LoginViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private val loginUser = MutableLiveData<User>()

    var userData = loginUser.switchMap {
        laundryRepository.postLogin(
            User(
                email = it.email,
                password = it.password,
            )
        ).asLiveData()
    }

    fun loginUser(user: User) {
        loginUser.postValue(user)
    }
}