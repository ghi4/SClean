package com.project.laundryapp.ui.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundryOrderAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryOrderInput
import com.project.laundryapp.core.data.remote.response.laundry.LaundryServiceResponse
import com.project.laundryapp.databinding.ActivityPaymentBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.address.AddressActivity
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
            when(data){
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
                    Utils.showToast(this, Utils.parseError(data.message.toString()))
                }
            }
        })
    }

    private fun setupUI() {
        clearStatusInformation()

        val laundryId = intent.getStringExtra(Const.KEY_LAUNDRY_ID)
        val orderedService = intent.getParcelableArrayListExtra<LaundryOrderInput>(Const.KEY_SERVICE_ORDERED)

        val user = Utils.getSharedPref(this)
        val data = LaundryServiceResponse(
                idLayanan = user.id,
                idLaundry = laundryId,
                daftarLayanan = orderedService
        )

        orderAdapter = LaundryOrderAdapter()
        orderAdapter.setList(orderedService as ArrayList<LaundryOrderInput>)

        with(binding){
            //Counting totalPrice
            val subTotalPrice = Utils.countPrice(orderedService)
            val totalPrice = subTotalPrice + Const.SHIPMENT_PRICE

            //Initializing value
            tvUserAddress.text = Utils.parseFullAddress(user)
            tvPaymentSubTotal.text = Utils.parseIntToCurrency(subTotalPrice)
            tvPaymentShipment.text = Utils.parseIntToCurrency(Const.SHIPMENT_PRICE)
            tvPaymentTotal.text = Utils.parseIntToCurrency(totalPrice)

            //RecyclerView
            rvPaymentOrderList.layoutManager = LinearLayoutManager(this@PaymentActivity)
            rvPaymentOrderList.hasFixedSize()
            rvPaymentOrderList.adapter = orderAdapter

            //Change Address
            tvChangeAddress.setOnClickListener {
                val intent = Intent(this@PaymentActivity, AddressActivity::class.java)
                intent.putExtra(AddressActivity.ADDRESS_CHANGE_KEY, 2)
                startActivity(intent)
            }

            //Payment Confirmation
            btPaymentConfirmation.setOnClickListener {
                viewModel.triggerPayment(data)
            }
        }
    }

    private fun showLoading() {
        binding.progressBarPayment.visibility = View.VISIBLE
    }

    private fun clearStatusInformation() {
        binding.progressBarPayment.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()

        val user = Utils.getSharedPref(this)
        binding.tvUserAddress.text = Utils.parseFullAddress(user)
    }


}