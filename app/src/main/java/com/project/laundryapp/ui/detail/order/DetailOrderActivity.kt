package com.project.laundryapp.ui.detail.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    private fun setupUI(historyId: String) {
        hideView()

        //Adapter
        orderAdapter = LaundryOrderAdapter()

        //Binding
        with(binding) {
            rvPaymentOrderList.layoutManager = LinearLayoutManager(this@DetailOrderActivity)
            rvPaymentOrderList.hasFixedSize()
            rvPaymentOrderList.adapter = orderAdapter

            btPaymentCancel.setOnClickListener {
                viewModel.triggerDelete(historyId)
            }
        }
    }

    private fun getData() {
        val user = Utils.getSharedPref(this)
        binding.tvUserAddress.text = Utils.parseFullAddress(user)

        viewModel.historyDetail.observe(this, {data ->
            when(data) {
                is Resource.Loading -> {
                    hideView()
                    showLoading()
                }

                is Resource.Success -> {
                    val orderList = data.data?.data?.daftarLayanan?.map {
                        LaundryOrderInput(
                                it.idLayanan.toString(),
                                it.namaLayanan.toString(),
                                it.qty,
                                it.harga
                        )
                    }
                    orderAdapter.setList(orderList as ArrayList<LaundryOrderInput>)

                    val subTotalPrice = Utils.countPrice(orderList)
                    val totalPrice = subTotalPrice + Const.SHIPMENT_PRICE

                    with(binding) {
                        tvPaymentEstimationDays.text = 5.toString()
                        tvPaymentShipment.text = Utils.parseIntToCurrency(Const.SHIPMENT_PRICE)
                        tvPaymentSubTotal.text = Utils.parseIntToCurrency(subTotalPrice)
                        tvPaymentTotalPrice.text = Utils.parseIntToCurrency(totalPrice)
                    }

                    showView()
                }

                is Resource.Error -> {
                    hideView()
                    showMessage(data.message)
                }
            }
        })

        viewModel.deleteData.observe(this, { data ->
            when (data) {
                is Resource.Loading -> {
                    showLoading()
                }

                is Resource.Success -> {
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

    private fun hideView() {
        with(binding){
            scrollViewDetailOrder.visibility = View.INVISIBLE
            cardViewDetailOrder.visibility = View.INVISIBLE
        }
    }

    private fun showView() {
        binding.scrollViewDetailOrder.visibility = View.VISIBLE
        binding.cardViewDetailOrder.visibility = View.VISIBLE
        binding.constraintViewDetailOrder.visibility = View.INVISIBLE
        clearStatusInformation()
        Anim.crossFade(binding.constraintViewDetailOrder)
    }

    private fun showLoading() {
        with(binding.statusDetailOrder){
            progressBar.visibility = View.VISIBLE
            tvMessage.visibility = View.INVISIBLE
            tvRetry.visibility = View.INVISIBLE
        }
    }

    private fun showMessage(message: String? = getString(R.string.an_error_occured)) {
        with(binding.statusDetailOrder){
            progressBar.visibility = View.INVISIBLE
            tvMessage.visibility = View.VISIBLE
            tvRetry.visibility = View.VISIBLE

            tvMessage.text = message
        }
    }

    private fun clearStatusInformation() {
        with(binding.statusDetailOrder){
            progressBar.visibility = View.INVISIBLE
            tvMessage.visibility = View.INVISIBLE
            tvRetry.visibility = View.INVISIBLE
        }
    }
}