package com.project.laundryapp.ui.detail.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundryOrderAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryOrderInput
import com.project.laundryapp.databinding.ActivityDetailOrderBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.utils.Anim
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import org.koin.android.ext.android.inject

class DetailOrderActivity : AppCompatActivity() {

    private val viewModel: DetailOrderViewModel by inject()
    private lateinit var binding: ActivityDetailOrderBinding
    private lateinit var orderAdapter: LaundryOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyId = intent.getStringExtra(Const.KEY_LAUNDRY_HISTORY_ID)
        if (historyId != null) {
            viewModel.triggerCall(historyId)
            setupUI(historyId)
            getData()
        }
    }

    private fun getData() {
        viewModel.historyDetail.observe(this, {data ->
            Log.d("DETAIL ORDER", """
                        =============================================================
                        BASE:
                        $data
                        
                        MESSAGE:
                        ${data.message}
                        
                        MESSAGE RESPONSE:
                        ${data.data?.message}
                        
                        MESSAGE ERROR:
                        ${data.data?.error}
                        
                        DATA:
                        ${data.data?.data}
                        =============================================================
                    """)
            when(data) {
                is Resource.Loading -> {
                    Utils.showToast(this, "Memuat data.")
                }

                is Resource.Success -> {
                    Utils.showToast(this, "Berhasil.")

                    val orderList = data.data?.data?.daftarLayanan?.map {
                        LaundryOrderInput(
                                it.idLayanan.toString(),
                                it.namaLayanan.toString(),
                                it.qty,
                                it.harga
                        )
                    }
                    orderAdapter.setList(orderList as ArrayList<LaundryOrderInput>)

                    val totalPrice: Int
                    var subTotalPrice = 0

                    orderList.map {
                        val price = it.harga ?: 0
                        val qty = it.jumlah ?: 0
                        subTotalPrice += price * qty
                    }

                    totalPrice = subTotalPrice + Const.SHIPMENT_PRICE

                    with(binding) {
                        tvPaymentEstimationDays.text = 5.toString()
                        tvPaymentShipment.text = Utils.parseIntToCurrency(Const.SHIPMENT_PRICE)
                        tvPaymentSubTotal.text = Utils.parseIntToCurrency(subTotalPrice)
                        tvPaymentTotalPrice.text = Utils.parseIntToCurrency(totalPrice)

                        Anim.crossFade(root)
                    }
                }

                is Resource.Error -> {
                    Utils.showToast(this, data.message.toString())
                }
            }
        })

        viewModel.deleteData.observe(this, { data ->
            when (data) {
                is Resource.Loading -> {
                    Utils.showToast(this, "Mohon tunggu.")
                }
                is Resource.Success -> {
                    Utils.showToast(this, "Berhasil!")

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(MainActivity.FRAGMENT_ID_KEY, R.id.navigation_history)
                    startActivity(intent)
                }
                is Resource.Error -> {
                    Utils.showToast(this, data.message.toString())
                }
            }
        })
    }

    private fun setupUI(historyId: String) {
        orderAdapter = LaundryOrderAdapter()

        with(binding) {
            root.visibility = View.INVISIBLE

            rvPaymentOrderList.layoutManager = LinearLayoutManager(this@DetailOrderActivity)
            rvPaymentOrderList.hasFixedSize()
            rvPaymentOrderList.adapter = orderAdapter

            btPaymentCancel.setOnClickListener {
                viewModel.triggerDelete(historyId)
            }
        }

    }
}