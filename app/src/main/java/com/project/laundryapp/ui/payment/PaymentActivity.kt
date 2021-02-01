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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        getData()
    }

    private fun getData() {
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
        orderAdapter.setList(orderedService as ArrayList<LaundryOrderInput>)

        with(binding){
            val totalPrice: Int
            var subTotalPrice = 0

            //Counting totalPrice
            orderedService.map {
                val price = it.harga ?: 0
                val qty = it.jumlah ?: 0
                subTotalPrice += price * qty
            }
            totalPrice = subTotalPrice + Const.SHIPMENT_PRICE

            //Initializing value
            tvUserAddress.text = user.alamat
            tvPaymentSubTotal.text = Utils.parseIntToCurrency(subTotalPrice)
            tvPaymentShipment.text = Utils.parseIntToCurrency(Const.SHIPMENT_PRICE)
            tvPaymentTotal.text = Utils.parseIntToCurrency(totalPrice)

            //RecyclerView
            rvPaymentOrderList.layoutManager = LinearLayoutManager(this@PaymentActivity)
            rvPaymentOrderList.hasFixedSize()
            rvPaymentOrderList.adapter = orderAdapter

            //Change Address
            tvChangeAddress.setOnClickListener {

            }

            //Payment Confirmation
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