package com.project.laundryapp.core.utils

object ResponseMessage {
    private val successMessage = listOf(
            "Berhasil",
            "Success"
    )

    private val failMessage = listOf(
            "Gagal",
            "Failed"
    )

    fun isSuccess(input: String): Boolean {
        var status = false

        successMessage.map {
            if (input.contains(it, ignoreCase = true))
                status = true
        }

        return status
    }
}