package com.project.laundryapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.LaundryRepository
import com.project.laundryapp.core.data.local.User

class RegisterViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private val registerUser = MutableLiveData<User>()

    var userData = registerUser.switchMap {
        laundryRepository.postRegister(
                User(
                        email = it.email,
                        namaLengkap = it.namaLengkap,
                        nomorHp = it.nomorHp,
                        password = it.password,
                )
        ).asLiveData()
    }

    fun registerUser(user: User) {
        registerUser.postValue(user)
    }
}