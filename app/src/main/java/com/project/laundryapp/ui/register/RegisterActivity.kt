package com.project.laundryapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.laundryapp.R
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.User
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
            val userRegister = User(
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
                            startActivity(Intent(this, AddressActivity::class.java))
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