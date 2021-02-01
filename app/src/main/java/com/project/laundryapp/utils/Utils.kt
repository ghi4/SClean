package com.project.laundryapp.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.project.laundryapp.core.data.local.User

object Utils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun putSharedPref(activity: Activity, user: User){
        Log.d("Fragment", """
            PUT ENGINE
            ${user.id},
            ${user.email},
            ${user.namaLengkap},
            ${user.photo}
        """.trimIndent())

        val shared = activity.getSharedPreferences("ProfileData", Context.MODE_PRIVATE) ?: return
        with(shared.edit()){
            val json = userToJson(user)
            putString(Const.SHARED_PREFS_USER, json)
            commit()
        }
    }

    fun getSharedPref(activity: Activity): User{
        val shared = activity.getSharedPreferences("ProfileData", Context.MODE_PRIVATE)
        val jsonUser = shared.getString(Const.SHARED_PREFS_USER, "No Data") ?: "No Data"

        Log.d("Fragment", "GET ENGINE: $jsonUser")
        return if(jsonUser != "No Data"){
            jsonToUser(jsonUser)
        } else {
            User()
        }
    }

    fun parseError(input: String): String {
        return when {
            input.contains("402") -> {
                "Periksa kembali email dan password."
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

    fun isEmailValid(input: String): Boolean{
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