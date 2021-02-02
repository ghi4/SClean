package com.project.laundryapp.core.data.remote.response.promotion

import com.google.gson.annotations.SerializedName

data class PromotionResponse (
        @SerializedName("id")
        val idPromotion: String,

        @SerializedName("photo")
        val photoURL: String,

        @SerializedName("keterangan")
        val information: String,

        @SerializedName("tgl_awal")
        val startDate: String,

        @SerializedName("tgl_akhir")
        val endDate: String,

        @SerializedName("create_date")
        val createDate: String,

        @SerializedName("update_date")
        val updateDate: String
)