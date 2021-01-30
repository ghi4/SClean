package com.project.laundryapp.core.utils

import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.response.DataResponse
import com.project.laundryapp.core.data.remote.response.UserResponse

object DataMapper {
    fun remoteUserToLocal(input: DataResponse?): User = User(
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

    fun remoteUserToLocal(input: UserResponse?): User = User(
        input?.id,
        input?.email,
        input?.namaLengkap,
        input?.nomorHp,
        input?.photo,
        input?.password,
        input?.alamat,
        input?.kota,
        input?.kecamatan,
        input?.kelurahan,
        input?.kodePos,
        input?.keteranganAlamat
    )
}