package com.project.laundryapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.project.laundryapp.R
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.login.LoginActivity
import com.project.laundryapp.utils.Utils

class SplashActivity : AppCompatActivity() {

    private val splashDelay = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val user = Utils.getSharedPref(this)

            if (user.id == "Unknown") {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }

            finish()
        }, splashDelay)

    }
}