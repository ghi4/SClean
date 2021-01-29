package com.project.laundryapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.laundryapp.R
import com.project.laundryapp.databinding.ActivityLoginBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.tvGotoRegistration.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}