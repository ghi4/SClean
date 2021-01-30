package com.project.laundryapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.laundryapp.R
import com.project.laundryapp.core.data.remote.ApiResponse
import com.project.laundryapp.core.data.remote.response.UserResponse
import com.project.laundryapp.databinding.ActivityLoginBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.address.AddressActivity
import com.project.laundryapp.ui.register.RegisterActivity
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
        binding.btLogin.setOnClickListener {
            val user = UserResponse(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
            viewModel.loginUser(user)
        }

        binding.tvGotoRegistration.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observeUserLogin() {
        viewModel.userData.observe(this, { userResponse ->
            when(userResponse) {
                is ApiResponse.Loading -> {
                    Log.d("USER STATUS", "Loading")
                }

                is ApiResponse.Success -> {
                    val responseStatus = userResponse.data
                    val user = responseStatus.data.user

                    Log.d("USER STATUS", "" + responseStatus.message)
                    Log.d("USER STATUS", "" + responseStatus.error)

                    if(userResponse.data.message.contains("Success", ignoreCase = true)) {
                        if (!user.id.isNullOrEmpty()) {
                            Log.d("USER STATUS", user.id.toString())
                            val intent = Intent(this, AddressActivity::class.java)
                            startActivity(intent)
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