package com.project.laundryapp.ui.detail.laundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundryServiceAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryOrderInput
import com.project.laundryapp.core.data.remote.response.laundry.LaundryServiceResponse
import com.project.laundryapp.databinding.ActivityDetailLaundryBinding
import com.project.laundryapp.ui.payment.PaymentActivity
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

class DetailLaundryActivity : AppCompatActivity() {

    private val viewModel: DetailLaundryViewModel by inject()
    private lateinit var binding: ActivityDetailLaundryBinding
    private lateinit var serviceAdapter: LaundryServiceAdapter

    private var serviceOrdered = ArrayList<LaundryOrderInput>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val laundryId = intent.getStringExtra(Const.KEY_LAUNDRY_ID)
        if (laundryId != null) {
            viewModel.triggerCall(laundryId)
            setupUI(laundryId)
            getData()
        }
    }

    private fun setupUI(laundryId: String) {
        serviceAdapter = LaundryServiceAdapter()

        with(binding) {
            rvDetailServiceList.layoutManager = LinearLayoutManager(this@DetailLaundryActivity)
            rvDetailServiceList.hasFixedSize()
            rvDetailServiceList.adapter = serviceAdapter
            rvDetailServiceList.isNestedScrollingEnabled = false
        }

        serviceAdapter.onItemClick = {data ->
            val serviceData = LaundryOrderInput(
                data.namaLayanan.toString(),
                data.qty,
                data.harga
            )

            //Prevent duplicate data
            if(serviceOrdered.isEmpty())
                serviceOrdered.add(serviceData)
            else {
                var isNotDuplicate = true
                for(i in 0 until serviceOrdered.size){
                    if(serviceOrdered[i].idLayanan == serviceData.idLayanan) {
                        isNotDuplicate = false
                        serviceOrdered[i] = serviceData
                    }
                    if(serviceOrdered[i].jumlah == 0){
                        serviceOrdered.removeAt(i)
                    }
                }
                if (isNotDuplicate)
                    serviceOrdered.add(serviceData)
            }

        }

        binding.btDetailOrder.setOnClickListener {

            if(serviceOrdered.isNotEmpty()){
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra(Const.KEY_LAUNDRY_ID, laundryId)
                intent.putExtra(Const.KEY_SERVICE_ORDERED, serviceOrdered)
                startActivity(intent)
            } else {
                Utils.showToast(this, "Tidak ada layanan terpilih.")
            }
        }
    }

    private fun getData() {
        viewModel.laundryDetail.observe(this, {data ->
            Log.d("DETAIL LAUNDRY TAG", """
                BASE:
                $data
                
                RESOURCE:
                ${data.data}
                
                MESSAGE:
                ${data.message}
                
                MESSAGE RESPONSE:
                ${data.data?.message}
                                
                MESSAGE ERROR:
                ${data.data?.error}
                
                DATA:
                ${data.data?.data}
            """.trimIndent())
            when(data) {
                is Resource.Loading -> {
                    Utils.showToast(this, "Memuat data.")
                }

                is Resource.Success -> {
                    val dataStatus = data.data
                    val dataLaundry = dataStatus?.data

                    if(dataLaundry != null){
                        with(binding){
                            val openingHours = "Buka: ${dataLaundry.jamBuka} - ${dataLaundry.jamTutup}"
                            tvDetailTitle.text = dataLaundry.namaLaundry
                            tvDetailAddress.text = dataLaundry.alamat
                            tvDetailCount.text = 100.toString()
                            tvDetailRating.text = 4.5.toString()
                            tvDetailDescription.text = dataLaundry.deskripsi
                            tvDetailOpeningHours.text = openingHours

                            Picasso.get()
                                .load(Const.URL_BASE_IMAGE + Const.URL_SPECIFIED_IMAGE + dataLaundry.photo)
                                .placeholder(R.drawable.wide_image_placeholder)
                                .error(R.drawable.wide_image_placeholder)
                                .resize(Const.SQUARE_TARGET_SIZE, Const.SQUARE_TARGET_SIZE)
                                .into(ivDetailImage)
                        }

                        serviceAdapter.setList(dataLaundry.laundryService as ArrayList<LaundryServiceResponse>)
                    }

                }

                is Resource.Error -> {
                    Utils.showToast(this, data.message.toString())
                }
            }
        })
    }
}