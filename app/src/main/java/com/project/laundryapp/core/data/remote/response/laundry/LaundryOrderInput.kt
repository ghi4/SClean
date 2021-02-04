package com.project.laundryapp.core.data.remote.response.laundry

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaundryOrderInput(
        @SerializedName("id_layanan")
        @Expose
        var idLayanan: String,

        @SerializedName("nama_layanan")
        @Expose(serialize = false)
        var namaLayanan: String,

        @SerializedName("estimasi")
        @Expose(serialize = false)
        var estimasiPengerjaan: String,

        @SerializedName("satuan")
        @Expose(serialize = false)
        var satuan: String,

        @SerializedName("qty")
        @Expose
        var jumlah: Int? = 0,

        @SerializedName("harga")
        @Expose
        var harga: Int? = 0
) : Parcelable
