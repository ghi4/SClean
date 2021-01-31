package com.project.laundryapp.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.project.laundryapp.core.data.local.User
import java.util.*

object Utils {

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