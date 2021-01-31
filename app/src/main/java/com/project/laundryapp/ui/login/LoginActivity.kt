package com.project.laundryapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.project.laundryapp.R
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.databinding.ActivityLoginBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.address.AddressActivity
import com.project.laundryapp.ui.register.RegisterActivity
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by inject()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeUserLogin()
    }

    private fun setupUI() {

        //Button Login
        binding.btLogin.setOnClickListener {
            resetWarning()
            if(isInputValid()) {
                val user = User(
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString()
                )
                viewModel.loginUser(user)
            } else {
                Toast.makeText(this, "Periksa kembali data Anda.", Toast.LENGTH_SHORT).show()
            }
        }

        //Button Registration
        binding.tvGotoRegistration.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observeUserLogin() {
        viewModel.userData.observe(this, { dataPacket ->
            when(dataPacket) {
                is Resource.Loading -> {
                    Log.d("USER STATUS", "Loading")
                }

                is Resource.Success -> {
                    val user = dataPacket.data

                    Log.d("USER STATUS", "" + dataPacket.message)

                    if(user != null) {
                        Log.d("Fragment", """
                            LOGIN ACTIVITY
                                ${user.id},
                                ${user.email},
                                ${user.namaLengkap},
                                ${user.photo}
                            """.trimIndent())

                        Log.d("USER STATUS", "" + user.id)
                        Log.d("USER STATUS", "" + user.password)

                        Utils.putSharedPref(this, user)
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }

                is Resource.Error -> {
                    Log.d("USER STATUS", "" + dataPacket.message)
                }
            }
        })
    }

    private fun resetWarning() {
        binding.etEmail.error = null
        binding.etPassword.error = null
    }

    private fun isInputValid(): Boolean {
        var validity = true

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        // === Email ===
        if(email.isEmpty()){
            validity = false
            binding.etEmail.error = "Tidak boleh kosong."
        }
        if(!Utils.isEmailValid(email)) {
            validity = false
            binding.etEmail.error = "Email tidak valid."
        }

        // === Password ===
        if(password.isEmpty()) {
            validity = false
            binding.etPassword.error = "Tidak boleh kosong."
        }

        return validity
    }
}