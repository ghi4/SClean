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
import org.koin.android.viewmodel.ext.android.viewModel

class DetailOrderActivity : AppCompatActivity() {

    private val viewModel: DetailOrderViewModel by viewModel()
    private lateinit var binding: ActivityDetailOrderBinding
    private lateinit var orderAdapter: LaundryOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyId = intent.getStringExtra(Const.KEY_LAUNDRY_HISTORY_ID).toString()

        if (savedInstanceState == null) {
            viewModel.triggerCall(historyId)
        }

        setupUI(historyId)
        getData()
    }

    private fun setupUI(historyId: String) {
        hideView()

        //Adapter
        orderAdapter = LaundryOrderAdapter()

        //Binding
        with(binding) {
            rvPaymentOrderList.layoutManager = LinearLayoutManager(this@DetailOrderActivity)
            rvPaymentOrderList.isNestedScrollingEnabled = false
            rvPaymentOrderList.hasFixedSize()
            rvPaymentOrderList.adapter = orderAdapter
        }

        //Button Cancel
        binding.btPaymentCancel.setOnClickListener {
            viewModel.triggerDelete(historyId)
        }

        //Button Retry
        binding.statusDetailOrder.tvRetry.setOnClickListener {
            viewModel.triggerCall(historyId)
        }
    }

    private fun getData() {
        val user = Utils.getSharedPref(this)
        binding.tvUserAddress.text = Utils.parseFullAddress(user)

        viewModel.historyDetail.observe(this, { data ->
            when (data) {
                is Resource.Loading -> {
                    hideView()
                    showLoading()
                }

                is Resource.Success -> {
                    val dataLaundry = data.data?.data
                    val status = dataLaundry?.status
                    val orderList = dataLaundry?.daftarLayanan?.map {
                        LaundryOrderInput(
                                it.idLayanan.toString(),
                                it.namaLayanan.toString(),
                                it.estimasi.toString(),
                                it.satuan.toString(),
                                it.qty,
                                it.harga
                        )
                    }
                    orderAdapter.setList(orderList as ArrayList<LaundryOrderInput>)

                    val shipmentPrice = dataLaundry.biayaPengantaran ?: 0
                    val subTotalPrice = Utils.countPrice(orderList)
                    val totalPrice = subTotalPrice + shipmentPrice

                    with(binding) {
                        tvPaymentEstimationDays.text = Utils.getEstimationDays(orderList)
                        tvPaymentShipment.text = Utils.parseIntToCurrency(shipmentPrice)
                        tvPaymentSubTotal.text = Utils.parseIntToCurrency(subTotalPrice)
                        tvPaymentTotalPrice.text = Utils.parseIntToCurrency(totalPrice)

                        if(!canBeCanceled(status))
                            btPaymentCancel.visibility = View.GONE
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

                    clearStatusInformation()
                }

                is Resource.Error -> {
                    clearStatusInformation()
                    Utils.showToast(this, data.message.toString())
                }
            }
        })
    }
    
    private fun canBeCanceled(status: String?): Boolean {
        if(status == "0")
            return true
        return false
    }

    private fun hideView() {
        with(binding) {
            scrollViewDetailOrder.visibility = View.INVISIBLE
            linearLayout4.visibility = View.INVISIBLE
        }
    }

    private fun showView() {
        binding.scrollViewDetailOrder.visibility = View.VISIBLE
        binding.linearLayout4.visibility = View.VISIBLE
        binding.constraintViewDetailOrder.visibility = View.INVISIBLE
        clearStatusInformation()
        Anim.crossFade(binding.constraintViewDetailOrder)
    }

    private fun showLoading() {
        with(binding.statusDetailOrder) {
            progressBar.visibility = View.VISIBLE
            tvMessage.visibility = View.INVISIBLE
            tvRetry.visibility = View.INVISIBLE
        }
    }

    private fun showMessage(message: String? = getString(R.string.an_error_occured)) {
        with(binding.statusDetailOrder) {
            progressBar.visibility = View.INVISIBLE
            tvMessage.visibility = View.VISIBLE
            tvRetry.visibility = View.VISIBLE

            tvMessage.text = message
        }
    }

    private fun clearStatusInformation() {
        with(binding.statusDetailOrder) {
            progressBar.visibility = View.INVISIBLE
            tvMessage.visibility = View.INVISIBLE
            tvRetry.visibility = View.INVISIBLE
        }
    }
}