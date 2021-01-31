package com.project.laundryapp.core.data.local

data class Laundry(
    var idLaundry: String? = "Unknown",

    var namaLaundry: String? = "Unknown",

    var nomorTelepon: String? = "Unknown",

    var jamBuka: String? = "Unknown",

    var jamTutup: String? = "Unknown",

    var alamat: String? = "Unknown",

    var deskripsi: String? = "Unknown",

    var foto: String? = "Unknown",

    var daftarLayanan: List<LaundryService>? = emptyList()
)
