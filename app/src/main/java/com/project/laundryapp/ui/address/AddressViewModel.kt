package com.project.laundryapp.ui.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.LaundryRepository
import com.project.laundryapp.core.data.local.User

class AddressViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private val user = MutableLiveData<User>()

    var userData = user.switchMap {
        laundryRepository.postAddress(
                User(
                        alamat = it.alamat,
                        kota = it.kota,
                        kecamatan = it.kecamatan,
                        kelurahan = it.kelurahan,
                        kodePos = it.kodePos,
                        keteranganAlamat = it.keteranganAlamat,
                        id = it.id
                )
        ).asLiveData()
    }

    fun triggerPost(user: User) {
        this.user.postValue(user)
    }
}