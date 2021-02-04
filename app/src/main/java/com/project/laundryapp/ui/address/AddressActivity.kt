package com.project.laundryapp.ui.address

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.laundryapp.R
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.databinding.ActivityAddressBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.utils.Utils
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AddressActivity : AppCompatActivity() {

    private val viewModel: AddressViewModel by viewModel()
    private lateinit var binding: ActivityAddressBinding

    companion object {
        const val ADDRESS_CHANGE_KEY = "address change key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeUserRegister()
    }

    private fun setupUI() {
        clearStatusInformation()

        //Set value if data
        with(binding) {
            val user = Utils.getSharedPref(this@AddressActivity)

            val fullAddress = user.alamat.toString()
            val city = user.kota.toString()
            val districs = user.kecamatan.toString()
            val subDistrics = user.kelurahan.toString()
            val postalCode = user.kodePos.toString()
            val informationAddress = user.keteranganAlamat.toString()

            if (isAddressValid(fullAddress)) {
                etAddressFullName.setText(fullAddress)
            }
            if (isAddressValid(city)) {
                etAddressCity.setText(city)
            }
            if (isAddressValid(districs)) {
                etAddressDistricts.setText(districs)
            }
            if (isAddressValid(subDistrics)) {
                etAddressSubDistrict.setText(subDistrics)
            }
            if (isAddressValid(postalCode)) {
                etAddressPostalCode.setText(postalCode)
            }
            if (isAddressValid(informationAddress)) {
                etAddressInformation.setText(informationAddress)
            }
        }

        //Button Save Address
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
                if (fullAddress.isEmpty()) {
                    validity = false
                    etAddressFullName.error = getString(R.string.cannot_empty)
                }

                // === City ===
                if (city.isEmpty()) {
                    validity = false
                    etAddressCity.error = getString(R.string.cannot_empty)
                }

                // === Distric ===
                if (districs.isEmpty()) {
                    validity = false
                    etAddressDistricts.error = getString(R.string.cannot_empty)
                }

                // === Sub Distric ===
                if (subDistric.isEmpty()) {
                    validity = false
                    etAddressSubDistrict.error = getString(R.string.cannot_empty)
                }

                // === Postal Code ===
                if (postalCode.length != 5) {
                    validity = false
                    etAddressPostalCode.error = getString(R.string.must_be_5_characters)
                }
                if (!Utils.isNumeric(postalCode)) {
                    validity = false
                    etAddressPostalCode.error = getString(R.string.must_be_numeric)
                }
                if (postalCode.isEmpty()) {
                    validity = false
                    etAddressPostalCode.error = getString(R.string.cannot_empty)
                }

                // === Address Info ===
                if (addressInfo.isEmpty()) {
                    validity = false
                    etAddressInformation.error = getString(R.string.cannot_empty)
                }
            }

            //Check if input is valid
            if (validity) {
                val user = Utils.getSharedPref(this)
                user.alamat = binding.etAddressFullName.text.toString().trim()
                user.kota = binding.etAddressCity.text.toString().trim()
                user.kecamatan = binding.etAddressDistricts.text.toString().trim()
                user.kelurahan = binding.etAddressSubDistrict.text.toString().trim()
                user.kodePos = binding.etAddressPostalCode.text.toString().trim()
                user.keteranganAlamat = binding.etAddressInformation.text.toString().trim()

                viewModel.triggerPost(user)
                Utils.putSharedPref(this, user)
            } else {
                Toast.makeText(this, getString(R.string.please_recheck), Toast.LENGTH_SHORT).show()
            }
        }

        //Button Skip
        binding.btAddressSkip.setOnClickListener {
            val previousActivity = intent.getIntExtra(ADDRESS_CHANGE_KEY, 0)

            if (previousActivity == 0) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                finish()
            }
        }
    }

    private fun observeUserRegister() {
        viewModel.userData.observe(this, { dataPacket ->
            when (dataPacket) {
                is Resource.Loading -> {
                    showLoading()
                }

                is Resource.Success -> {
                    val previousActivity = intent.getIntExtra(ADDRESS_CHANGE_KEY, 0)
                    val user = dataPacket.data

                    if (user != null) {
                        when (previousActivity) {
                            0 -> {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                            1 -> {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra(MainActivity.FRAGMENT_ID_KEY, R.id.navigation_profile)
                                startActivity(intent)
                            }
                            else -> {
                                finish()
                            }
                        }
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
        binding.progressBarAddress.visibility = View.VISIBLE
    }

    private fun clearStatusInformation() {
        binding.progressBarAddress.visibility = View.INVISIBLE
    }

    private fun isAddressValid(input: String): Boolean {
        return !input.contains("Unknown", true) && input.isNotEmpty() && input.isNotBlank()
    }
}