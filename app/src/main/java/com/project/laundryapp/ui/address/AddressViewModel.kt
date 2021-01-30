package com.project.laundryapp.ui.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.response.UserResponse

class AddressViewModel(private val remoteDataSource: RemoteDataSource): ViewModel() {

    private val addressUser = MutableLiveData<UserResponse>()
    
    var userData = addressUser.switchMap {
        remoteDataSource.postAddress(
            UserResponse(
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

    fun addressUser(userResponse: UserResponse) {
        addressUser.postValue(userResponse)
    }
}