package com.project.laundryapp.core.data.local

data class LaundryOrder(
    var idPesanan: String? = "Unknown",

    var nomorPesanan: String? = "Unknown",

    var idUser: String? = "Unknown",

    var idLaundry: String? = "Unknown",

    var tanggalPesan: String? = "Unknown",

    var total: String? = "Unknown",

    var status: String? = "Unknown",

    var tanggalBayar: String? = "Unknown",

    var tanggalStatus: String? = "Unknown",

    var tanggalPengantaran: String? = "Unknown",

    var catatanOrder: String? = "Unknown"
)
