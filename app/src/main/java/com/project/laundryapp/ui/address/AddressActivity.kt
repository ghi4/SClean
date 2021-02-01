package com.project.laundryapp.ui.address

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.laundryapp.R
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.databinding.ActivityAddressBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.utils.Utils
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
            var validity = true

            with(binding) {
                etAddressFullName.error = null
                etAddressCity.error = null
                etAddressDistricts.error = null
                etAddressSubDistrict.error = null
                etAddressPostalCode.error = null
                etAddressInformation.error = null

                val fullAddress = etAddressFullName.text.toString()
                val city = etAddressCity.text.toString()
                val districs = etAddressDistricts.text.toString()
                val subDistric = etAddressSubDistrict.text.toString()
                val postalCode = etAddressPostalCode.text.toString()
                val addressInfo = etAddressInformation.text.toString()

                // === Full Address ===
                if(fullAddress.isEmpty()) {
                    validity = false
                    etAddressFullName.error = "Tidak boleh kosong."
                }

                // === City ===
                if(city.isEmpty()) {
                    validity = false
                    etAddressCity.error = "Tidak boleh kosong."
                }

                // === Distric ===
                if(districs.isEmpty()) {
                    validity = false
                    etAddressDistricts.error = "Tidak boleh kosong."
                }

                // === Sub Distric ===
                if(subDistric.isEmpty()) {
                    validity = false
                    etAddressSubDistrict.error = "Tidak boleh kosong."
                }

                // === Postal Code ===
                if(postalCode.length != 5) {
                    validity = false
                    etAddressPostalCode.error = "Harus 5 karakter."
                }
                if(!Utils.isNumeric(postalCode)) {
                    validity = false
                    etAddressPostalCode.error = "Harus berupa angka."
                }
                if(postalCode.isEmpty()) {
                    validity = false
                    etAddressPostalCode.error = "Tidak boleh kosong."
                }

                // === Address Info ===
                if(addressInfo.isEmpty()) {
                    validity = false
                    etAddressInformation.error = "Tidak boleh kosong."
                }

            }


            if(validity) {
                val user = Utils.getSharedPref(this)
                user.alamat = binding.etAddressFullName.text.toString()
                user.kota = binding.etAddressCity.text.toString()
                user.kecamatan = binding.etAddressDistricts.text.toString()
                user.kelurahan = binding.etAddressSubDistrict.text.toString()
                user.kodePos = binding.etAddressPostalCode.text.toString()
                user.keteranganAlamat = binding.etAddressInformation.text.toString()

                viewModel.triggerPost(user)
            } else {
                Toast.makeText(this, "Periksa kembali userData Anda.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btAddressSkip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun observeUserRegister() {
        viewModel.userData.observe(this, { dataPacket ->
            Log.d("ADDRESS TAG", """
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

                    Utils.showToast(this, "Selamat datang!")

                    if(user != null) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                is Resource.Error -> {
                    Utils.showToast(this, dataPacket.message.toString())
                }
            }
        })
    }
}