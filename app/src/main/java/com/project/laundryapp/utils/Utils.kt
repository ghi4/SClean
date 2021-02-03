package com.project.laundryapp.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.project.laundryapp.R
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.response.laundry.LaundryOrderInput
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

object Utils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun putSharedPref(activity: Activity, user: User) {
        val shared = activity.getSharedPreferences("ProfileData", Context.MODE_PRIVATE) ?: return
        with(shared.edit()) {
            val json = userToJson(user)
            putString(Const.SHARED_PREFS_USER, json)
            commit()
        }
    }

    fun getSharedPref(activity: Activity): User {
        val shared = activity.getSharedPreferences("ProfileData", Context.MODE_PRIVATE)
        val jsonUser = shared.getString(Const.SHARED_PREFS_USER, "No Data") ?: "No Data"

        return if (jsonUser != "No Data") {
            jsonToUser(jsonUser)
        } else {
            User()
        }
    }

    fun getEstimationDays(input: ArrayList<LaundryOrderInput>): String {
        var biggest = 0

        try {
            input.map {
                if(biggest < it.estimasiPengerjaan.toInt())
                    biggest = it.estimasiPengerjaan.toInt()
            }
        } catch (e: Exception) {
            biggest = 9
        }

        return "$biggest Hari"
    }

    fun parseFullAddress(user: User): String {
        val address = "${user.alamat}, " +
                "Kel. ${user.kelurahan}, " +
                "Kec. ${user.kecamatan}, " +
                "Kota ${user.kota}, " +
                "${user.kodePos}. " +
                "${user.keteranganAlamat}."
        val isNotValid = address.contains("Unknown")

        return if (isNotValid) {
            "Alamat tidak valid."
        } else {
            address
        }
    }

    fun isAddressValid(user: User): Boolean {
        val address = "${user.alamat}, " +
                "Kel. ${user.kelurahan}, " +
                "Kec. ${user.kecamatan}, " +
                "Kota ${user.kota}, " +
                "Kode Pos ${user.kodePos}, " +
                "${user.keteranganAlamat}"
        return !address.contains("Unknown")
    }

    fun parseStatus(input: String): String {
        val status = listOf(
                "Menunggu Konfirmasi",
                "Menerima Pesanan",
                "Pesanan Selesai",
                "Pesanan Dibatalkan"
        )

        return try {
            status[input.toInt()]
        } catch (e: Exception) {
            input
        }
    }

    fun colorBackgroundByStatus(input: String): Int {
        val backgroundColor = listOf(
                R.drawable.textview_roundedcorner_yellow,
                R.drawable.textview_roundedcorner_lightblue,
                R.drawable.textview_roundedcorner_green,
                R.drawable.textview_roundedcorner_red
        )

        return try {
            backgroundColor[input.toInt()]
        } catch (e: Exception) {
            R.drawable.textview_roundedcorner_grey
        }
    }

    fun countPrice(input: ArrayList<LaundryOrderInput>): Int {
        var count = 0
        input.map {
            val price = it.harga ?: 0
            val qty = it.jumlah ?: 0
            count += price * qty
        }
        return count
    }

    fun colorTextByStatus1(input: String): Int {
        val textColor = listOf(
                R.color.yellow_900,
                R.color.lightblue_900,
                R.color.green_900,
                R.color.red_900
        )

        return try {
            textColor[input.toInt()]
        } catch (e: Exception) {
            R.color.grey_900
        }
    }

    fun colorTextByStatus(input: String): Int {
        val textColor = listOf(
                "#f57f17",
                "#01579b",
                "#1b5e20",
                "#b71c1c"
        )

        return try {
            Color.parseColor(textColor[input.toInt()])
        } catch (e: Exception) {
            Color.parseColor("#212121")
        }
    }

    fun parseError(input: String): String {
        return when {
            input.contains("402") -> {
                "Pastikan email dan password benar."
            }
            input.contains("Unable to resolve host") -> {
                "Masalah koneksi"
            }
            else -> {
                input
            }
        }
    }

    //Convert from 08:00:00 to 08:00
    fun parseHours(input: String): String {
        return try {
            val splitResult = input.split(":")
            splitResult[0] + ":" + splitResult[1]
        } catch (e: Exception) {
            input
        }
    }

    fun parseIntToCurrency(input: Int?): String {
        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance("IDR")
        format.minimumFractionDigits = 0
        val resultDot = format.format(input).replace(',', '.')
        return resultDot.replace("IDR", "Rp")
    }

    fun parseIntToWeight(qty: Int?, weightType: String): String {
        return "$qty $weightType"
    }

    fun findBiggest(input1: Int, input2: Int): Int {
        return if (input1 > input2) {
            input1
        } else {
            input2
        }
    }

    fun isEmailValid(input: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    fun isNumeric(input: String): Boolean {
        return input.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    private fun userToJson(user: User): String {
        return Gson().toJson(user)
    }

    private fun jsonToUser(string: String): User {
        return Gson().fromJson(string, User::class.java)
    }
}