package com.project.laundryapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.laundryapp.R
import com.project.laundryapp.core.data.remote.ApiResponse
import com.project.laundryapp.core.data.remote.response.UserResponse
import com.project.laundryapp.core.data.remote.retrofit.RetrofitInterface
import com.project.laundryapp.databinding.ActivityRegisterBinding
import com.project.laundryapp.ui.address.AddressActivity
import com.project.laundryapp.ui.login.LoginActivity
import org.koin.android.ext.android.bind
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
            val userRegister = UserResponse(
                namaLengkap = binding.etFullName.text.toString(),
                email = binding.etRegisterEmail.text.toString(),
                password = binding.etRegisterPassword.text.toString(),
                nomorHp = binding.etRegisterPhone.text.toString()
            )

            viewModel.registerUser(userRegister)
        }

        binding.tvGotoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun observeUserRegister() {
        viewModel.userData.observe(this, { userResponse ->
            when(userResponse) {
                is ApiResponse.Loading -> {
                    Log.d("USER STATUS", "Loading")
                }

                is ApiResponse.Success -> {
                    val responseStatus = userResponse.data

                    Log.d("USER STATUS", responseStatus.message)

                    if(userResponse.data.message.contains("Berhasil", ignoreCase = true)) {
                        if (!responseStatus.data.idUser.isNullOrEmpty()) {
                            Log.d("USER STATUS", responseStatus.data.idUser)
                            Log.d("USER STATUS", responseStatus.data.password)
                            startActivity(Intent(this, AddressActivity::class.java))
                        }
                    }
                }

                is ApiResponse.Error -> {
                    Log.d("USER STATUS", userResponse.message)
                }
            }
        })
    }
}