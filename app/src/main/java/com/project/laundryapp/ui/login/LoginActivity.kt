package com.project.laundryapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.laundryapp.R
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.databinding.ActivityLoginBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.register.RegisterActivity
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
        getData()
    }

    private fun setupUI() {
        clearStatusInformation()

        //Button Login
        binding.btLogin.setOnClickListener {
            //Clean warning from EditText
            resetWarning()

            //Check input validity
            if (isInputValid()) {
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

    private fun getData() {
        viewModel.userData.observe(this, { dataPacket ->

            when (dataPacket) {
                is Resource.Loading -> {
                    showLoading()
                }

                is Resource.Success -> {
                    val user = dataPacket.data

                    if (user != null) {
                        //Save data to shared preference
                        Utils.putSharedPref(this, user)

                        Utils.showToast(this, getString(R.string.welcome_back))

                        //Intent to MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    clearStatusInformation()
                }

                is Resource.Error -> {
                    clearStatusInformation()
                    Utils.showToast(this, dataPacket.message.toString())
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
        if (email.isEmpty()) {
            validity = false
            binding.etEmail.error = getString(R.string.cannot_empty)
        }
        if (!Utils.isEmailValid(email)) {
            validity = false
            binding.etEmail.error = getString(R.string.email_invalid)
        }

        // === Password ===
        if (password.isEmpty()) {
            validity = false
            binding.etPassword.error = getString(R.string.cannot_empty)
        }

        return validity
    }

    private fun showLoading() {
        binding.progressBarLogin.visibility = View.VISIBLE
    }

    private fun clearStatusInformation() {
        binding.progressBarLogin.visibility = View.INVISIBLE
    }
}