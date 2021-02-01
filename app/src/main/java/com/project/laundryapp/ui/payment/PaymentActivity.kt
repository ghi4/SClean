package com.project.laundryapp.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.core.adapter.LaundryOrderAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryOrderInput
import com.project.laundryapp.core.data.remote.response.laundry.LaundryServiceResponse
import com.project.laundryapp.databinding.ActivityPaymentBinding
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import org.koin.android.ext.android.inject

class PaymentActivity : AppCompatActivity() {

    private val viewModel: PaymentViewModel by inject()
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var orderAdapter: LaundryOrderAdapter
    private val estimationDays = 5
    private val shipmentPrice = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()

        viewModel.orderResponse.observe(this, {data ->
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
            when(data){
                is Resource.Loading -> {
                    Utils.showToast(this, "Mohon tunggu.")
                }

                is Resource.Success -> {
                    Utils.showToast(this, "Sukses!")
                }

                is Resource.Error -> {
                    Utils.showToast(this, data.message.toString())
                }
            }
        })
    }

    private fun setupUI() {
        val laundryId = intent.getStringExtra(Const.KEY_LAUNDRY_ID)
        val orderedService = intent.getParcelableArrayListExtra<LaundryOrderInput>(Const.KEY_SERVICE_ORDERED)
        val user = Utils.getSharedPref(this)

        orderAdapter = LaundryOrderAdapter()
        if (orderedService != null) {
            orderAdapter.setList(orderedService)
        }

        with(binding){
            var totalPrice = 0
            var subTotalPrice = 0

            orderedService?.map {
                val price = it.harga ?: 0
                val qty = it.jumlah ?: 0
                subTotalPrice += price * qty
            }
            totalPrice = subTotalPrice + shipmentPrice

            tvUserAddress.text = user.alamat

            tvPaymentSubTotal.text = "Rp $subTotalPrice"
            tvPaymentShipment.text = "Rp $shipmentPrice"
            tvPaymentTotal.text = "Rp $totalPrice"

            rvPaymentOrderList.layoutManager = LinearLayoutManager(this@PaymentActivity)
            rvPaymentOrderList.hasFixedSize()
            rvPaymentOrderList.adapter = orderAdapter

            tvChangeAddress.setOnClickListener {

            }

            btPaymentConfirmation.setOnClickListener {
                val data = LaundryServiceResponse(
                    idLayanan = user.id,
                    idLaundry = laundryId,
                    daftarLayanan = orderedService
                )

                viewModel.triggerPayment(data)
            }
        }

    }
}