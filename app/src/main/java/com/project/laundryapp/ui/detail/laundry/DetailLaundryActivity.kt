package com.project.laundryapp.ui.detail.laundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundryServiceAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.LaundryService
import com.project.laundryapp.core.data.remote.response.LaundryDataResponse
import com.project.laundryapp.core.data.remote.response.LaundryServiceInput
import com.project.laundryapp.core.data.remote.response.LaundryServiceResponse
import com.project.laundryapp.databinding.ActivityDetailLaundryBinding
import org.koin.android.ext.android.inject

class DetailLaundryActivity : AppCompatActivity() {

    private val viewModel: DetailLaundryViewModel by inject()
    private lateinit var binding: ActivityDetailLaundryBinding
    private lateinit var serviceAdapter: LaundryServiceAdapter

    private var serviceSelected = ArrayList<LaundryServiceInput>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val laundryId = intent.getStringExtra("KEY_INI")
        Log.d("HHWZ", "DETAIL ID: $laundryId")
        if (laundryId != null) {
            viewModel.triggerCall(laundryId)
        }

        setupUI()
        getData()
    }

    private fun setupUI() {
        serviceAdapter = LaundryServiceAdapter()

        with(binding) {
            rvDetailServiceList.layoutManager = LinearLayoutManager(this@DetailLaundryActivity)
            rvDetailServiceList.hasFixedSize()
            rvDetailServiceList.adapter = serviceAdapter
            rvDetailServiceList.isNestedScrollingEnabled = false
        }

        serviceAdapter.onItemClick = {onChangedService ->
            val serviceData = LaundryServiceInput(
                onChangedService.namaLayanan.toString(),
                onChangedService.qty,
                onChangedService.harga
            )

            //Prevent duplicate data
            if(serviceSelected.isEmpty())
                serviceSelected.add(serviceData)
            else {
                var isNotDuplicate = true
                for(i in 0 until serviceSelected.size){
                    if(serviceSelected[i].idLayanan == serviceData.idLayanan) {
                        isNotDuplicate = false
                        serviceSelected[i] = serviceData
                        break
                    }
                }
                if (isNotDuplicate)
                    serviceSelected.add(serviceData)
            }

            Log.d("MYGGWP", "RAW: " + onChangedService.toString())
            Log.d("MYGGWP", "LIST: " + serviceSelected.toString())
        }

        binding.btDetailOrder.setOnClickListener {
        }
    }

    private fun getData() {
        viewModel.laundryDetail.observe(this, {data ->
            Log.d("HHWZ", "" + data.toString())
            when(data) {
                is Resource.Loading -> {
                    Log.d("HHWZ", "ON LOADING")
                }

                is Resource.Success -> {
                    val dataStatus = data.data
                    val dataLaundry = dataStatus?.data

                    Log.d("HHWZ", "AAA: $data")
                    Log.d("HHWZ", "BBB: " + dataStatus.toString())
                    Log.d("HHWZ", "CCC: " + dataLaundry.toString())

                    if(dataLaundry != null){
                        with(binding){
                            val openingHours = "Buka: ${dataLaundry.jamBuka} - ${dataLaundry.jamTutup}"
                            tvDetailTitle.text = dataLaundry.namaLaundry
                            tvDetailAddress.text = dataLaundry.alamat
                            tvDetailCount.text = 100.toString()
                            tvDetailRating.text = 4.5.toString()
                            tvDetailDescription.text = dataLaundry.deskripsi
                            tvDetailOpeningHours.text = openingHours
                        }

                        serviceAdapter.setList(dataLaundry.laundryService as ArrayList<LaundryServiceResponse>)
                    }
                }

                is Resource.Error -> {
                    Log.d("HHWZ", data.message.toString() )
                }
            }
        })
    }
}