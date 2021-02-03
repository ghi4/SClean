package com.project.laundryapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    private fun getData() {
        viewModel.userData.observe(this, { dataPacket ->
            Log.d("REGISTER TAG", """
                BASE:
                $dataPacket
                
                MESSAGE:
                ${dataPacket.message}
                
                DATA:
                ${dataPacket.data}
                
            """.trimIndent())

            when(dataPacket) {
                is Resource.Loading -> {
                    Utils.showToast(this, "Mohon tunggu.")
                }

                is Resource.Success -> {
                    val user = dataPacket.data

                    if(user != null && !user.id.isNullOrEmpty()) {
                        //Save data to shared preference
                        Utils.putSharedPref(this, user)

                        Utils.showToast(this, "Registrasi sukses!")

                        //Intent to AddressActivity
                        val intent = Intent(this, AddressActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                is Resource.Error -> {
                    Utils.showToast(this, dataPacket.message.toString())
                }
            }
        })
    }
}