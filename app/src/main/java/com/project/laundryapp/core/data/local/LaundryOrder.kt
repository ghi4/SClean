package com.project.laundryapp.core.data.local

data class LaundryOrder(
    var idPesanan: String,

    var nomorPesanan: String,

    var idUser: String,

    var idLaundry: String,

    var tanggalPesan: String,

    var total: String,

    var status: String,

    var tanggalBayar: String,

    var tanggalStatus: String,

    var tanggalPengantaran: String,

    var catatanOrder: String
)
