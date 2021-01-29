package com.project.laundryapp.ui.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.laundryapp.R
import com.project.laundryapp.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}