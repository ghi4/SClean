package com.project.laundryapp.core.utils

import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.response.UserDataResponse
import com.project.laundryapp.core.data.remote.response.LaundryDataResponse
import com.project.laundryapp.core.data.remote.response.UserResponse

object DataMapper {
    fun remoteUserToLocal(input: UserDataResponse?): User = User(
        input?.idUser,
        input?.email,
        input?.namaLengkap,
        input?.nomorHp,
        null,
        input?.password,
        input?.alamat,
        input?.kota,
        input?.kecamatan,
        input?.kelurahan,
        input?.kodePos,
        input?.keteranganAlamat
    )
}