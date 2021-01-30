package com.project.laundryapp.ui.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.laundryapp.R
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.core.data.remote.ApiResponse
import com.project.laundryapp.core.data.remote.response.UserResponse
import com.project.laundryapp.databinding.ActivityAddressBinding
import com.project.laundryapp.ui.MainActivity
import org.koin.android.ext.android.inject

class AddressActivity : AppCompatActivity() {

    private val viewModel: AddressViewModel by inject()
    private lateinit var binding: ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeUserRegister()
    }

    private fun setupUI() {
        binding.btAddressSave.setOnClickListener {
            val user = User(
                alamat = binding.etAddressFullName.text.toString(),
                kota = binding.etAddressCity.text.toString(),
                kecamatan = binding.etAddressDistricts.text.toString(),
                kelurahan = binding.etAddressSubDistrict.toString(),
                kodePos = binding.etAddressPostalCode.toString(),
                keteranganAlamat = binding.etAddressInformation.text.toString()
            )

            viewModel.addressUser(user)
        }

        binding.btAddressSkip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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
                        Log.d("USER STATUS", "" + user.id)
                        Log.d("USER STATUS", "" + user.password)
                        //startActivity(Intent(this, AddressActivity::class.java))
                    }
                }

                is Resource.Error -> {
                    Log.d("USER STATUS", "" + dataPacket.message)
                }
            }
        })
    }
}