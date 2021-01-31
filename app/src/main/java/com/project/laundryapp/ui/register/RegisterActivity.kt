package com.project.laundryapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
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
        observeUserRegister()

    }

    private fun setupUI() {
        binding.btRegistration.setOnClickListener {
            var validity = true

            with(binding){
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
                if(fullName.isEmpty()){
                    validity = false
                    etFullName.error = "Tidak boleh kosong."
                }

                // === Email ===
                if(email.isEmpty()){
                    validity = false
                   etRegisterEmail.error = "Tidak boleh kosong."
                }
                if(!Utils.isEmailValid(email)) {
                    validity = false
                    etRegisterEmail.error = "Email tidak valid."
                }

                // === Phone ===
                if (phone.isEmpty()) {
                    validity = false
                    etRegisterPhone.error = "Tidak boleh kosong."
                }

                // === Password ===
                if (password.length < 5) {
                    validity = false
                    etRegisterRePassword.error = "Minimal 5 karakter."
                }
                if (rePassword != password) {
                    validity = false
                    etRegisterRePassword.error = "Password tidak cocok."
                }
                if(password.isEmpty()) {
                    validity = false
                    etRegisterPassword.error = "Tidak boleh kosong."
                }
                if(rePassword.isEmpty()) {
                    validity = false
                    etRegisterRePassword.error = "Tidak boleh kosong."
                }
            }

            if(validity) {
                val userRegister = User(
                        namaLengkap = binding.etFullName.text.toString(),
                        email = binding.etRegisterEmail.text.toString(),
                        password = binding.etRegisterPassword.text.toString(),
                        nomorHp = binding.etRegisterPhone.text.toString()
                )
                viewModel.registerUser(userRegister)
            } else {
                Toast.makeText(this, "Periksa kembali userData Anda.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGotoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun observeUserRegister() {
        viewModel.userData.observe(this, { dataPacket ->
            when(dataPacket) {
                is Resource.Loading -> {
                    Log.d("USER STATUS", "Loading")
                }

                is Resource.Success -> {
                    val user = dataPacket.data

                    Log.d("USER STATUS", "" + dataPacket.message)

                    if(user != null) {
                        if (!user.id.isNullOrEmpty()) {
                            Log.d("USER STATUS", "" + user.id)
                            Log.d("USER STATUS", "" + user.password)
                            val intent = Intent(this, AddressActivity::class.java)
                            intent.putExtra("KEY_ID", user.id)
                            startActivity(intent)
                        }
                    }
                }

                is Resource.Error -> {
                    Log.d("USER STATUS", "" + dataPacket.message)
                }
            }
        })
    }
}