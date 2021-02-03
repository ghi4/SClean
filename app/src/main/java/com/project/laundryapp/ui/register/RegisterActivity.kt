package com.project.laundryapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.laundryapp.R
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.databinding.ActivityRegisterBinding
import com.project.laundryapp.ui.address.AddressActivity
import com.project.laundryapp.ui.login.LoginActivity
import com.project.laundryapp.utils.Utils
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by inject()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        getData()
    }

    private fun setupUI() {
        clearStatusInformation()

        //Button Registration
        binding.btRegistration.setOnClickListener {
            var validity = true

            with(binding) {
                etFullName.error = null
                etRegisterEmail.error = null
                etRegisterPhone.error = null
                etRegisterPassword.error = null
                etRegisterRePassword.error = null

                val fullName = etFullName.text.toString()
                val email = etRegisterEmail.text.toString()
                val phone = etRegisterPhone.text.toString()
                val password = etRegisterPassword.text.toString()
                val rePassword = etRegisterRePassword.text.toString()

                // === Full Name ===
                if (fullName.isEmpty()) {
                    validity = false
                    etFullName.error = getString(R.string.cannot_empty)
                }

                // === Email ===
                if (email.isEmpty()) {
                    validity = false
                    etRegisterEmail.error = getString(R.string.cannot_empty)
                }
                if (!Utils.isEmailValid(email)) {
                    validity = false
                    etRegisterEmail.error = getString(R.string.email_invalid)
                }

                // === Phone ===
                if (phone.isEmpty()) {
                    validity = false
                    etRegisterPhone.error = getString(R.string.cannot_empty)
                }

                // === Password ===
                if (password.length < 5) {
                    validity = false
                    etRegisterRePassword.error = getString(R.string.minimum_5_characters)
                }
                if (rePassword != password) {
                    validity = false
                    etRegisterRePassword.error = getString(R.string.password_not_match)
                }
                if (password.isEmpty()) {
                    validity = false
                    etRegisterPassword.error = getString(R.string.cannot_empty)
                }
                if (rePassword.isEmpty()) {
                    validity = false
                    etRegisterRePassword.error = getString(R.string.cannot_empty)
                }
            }

            if (validity) {
                val userRegister = User(
                        namaLengkap = binding.etFullName.text.toString().trim(),
                        email = binding.etRegisterEmail.text.toString().trim(),
                        password = binding.etRegisterPassword.text.toString().trim(),
                        nomorHp = binding.etRegisterPhone.text.toString().trim()
                )
                viewModel.registerUser(userRegister)
            } else {
                Toast.makeText(this, getString(R.string.please_recheck), Toast.LENGTH_SHORT).show()
            }
        }

        //Button open Login
        binding.tvGotoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
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

                    if (user != null && !user.id.isNullOrEmpty()) {
                        //Save data to shared preference
                        Utils.putSharedPref(this, user)

                        //Intent to AddressActivity
                        val intent = Intent(this, AddressActivity::class.java)
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

    private fun showLoading() {
        binding.progressBarRegister.visibility = View.VISIBLE
    }

    private fun clearStatusInformation() {
        binding.progressBarRegister.visibility = View.INVISIBLE
    }

}