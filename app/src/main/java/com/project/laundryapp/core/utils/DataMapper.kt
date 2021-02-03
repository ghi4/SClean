package com.project.laundryapp.core.utils

import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.response.user.UserDataResponse

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