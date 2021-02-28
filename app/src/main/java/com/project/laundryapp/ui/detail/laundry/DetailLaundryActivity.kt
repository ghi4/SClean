package com.project.laundryapp.ui.detail.laundry

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.BuildConfig
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundryServiceAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryOrderInput
import com.project.laundryapp.core.data.remote.response.laundry.LaundryServiceResponse
import com.project.laundryapp.databinding.ActivityDetailLaundryBinding
import com.project.laundryapp.ui.payment.PaymentActivity
import com.project.laundryapp.utils.Anim
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

class DetailLaundryActivity : AppCompatActivity() {

    private val viewModel: DetailLaundryViewModel by viewModel()
    private lateinit var binding: ActivityDetailLaundryBinding
    private lateinit var serviceAdapter: LaundryServiceAdapter

    private var serviceOrdered = ArrayList<LaundryOrderInput>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val laundryId = intent.getStringExtra(Const.KEY_LAUNDRY_ID).toString()

        if (savedInstanceState == null) {
            viewModel.triggerCall(laundryId)
        }

        setupUI(laundryId)
        getData()
    }

    private fun setupUI(laundryId: String) {
        hideView()

        //Adapter
        serviceAdapter = LaundryServiceAdapter()

        //Binding
        with(binding) {
            rvDetailServiceList.layoutManager = LinearLayoutManager(this@DetailLaundryActivity)
            rvDetailServiceList.hasFixedSize()
            rvDetailServiceList.adapter = serviceAdapter
            rvDetailServiceList.isNestedScrollingEnabled = false
        }

        //When "layanan list" is clicked
        serviceAdapter.onItemClick = { data ->
            val serviceData = LaundryOrderInput(
                    data.idLayanan.toString(),
                    data.namaLayanan.toString(),
                    data.estimasi.toString(),
                    data.satuan.toString(),
                    data.qty,
                    data.harga
            )

            //Prevent duplicate data
            duplicatePreventor(serviceData)
        }

        //Button Retry
        binding.statusDetailLaundry.tvRetry.setOnClickListener {
            viewModel.triggerCall(laundryId)
        }
    }

    private fun getData() {
        viewModel.laundryDetail.observe(this, { data ->
            when (data) {
                is Resource.Loading -> {
                    hideView()
                    showLoading()
                }

                is Resource.Success -> {
                    showView()
                    val dataStatus = data.data
                    val dataLaundry = dataStatus?.data

                    if (dataLaundry != null) {
                        val laundryId = dataLaundry.idLaundry.toString()
                        val laundryName = dataLaundry.namaLaundry
                        val open = Utils.parseHours(dataLaundry.jamBuka.toString())
                        val close = Utils.parseHours(dataLaundry.jamTutup.toString())
                        val openingHours = "$open - $close"
                        val laundryAddress = dataLaundry.alamat
                        val laundryDescription = dataLaundry.deskripsi
                        val shipmentPrice = dataLaundry.biayaPengantaran
                        val shipmentPriceCurrency = Utils.parseIntToCurrency(shipmentPrice)

                        with(binding) {
                            include.tvCardDetailTitle.text = laundryName
                            include.tvCardDetailOpeningHours.text = openingHours
                            include.tvCardDetailAddress.text = laundryAddress
                            include.tvCardDetailDescription.text = laundryDescription
                            include.tvCardDetailShipmentPrice.text = shipmentPriceCurrency

                            Picasso.get()
                                    .load(BuildConfig.BASE_URL + BuildConfig.IMAGE_PATH_URL + dataLaundry.photo)
                                    .placeholder(R.drawable.wide_image_placeholder)
                                    .error(R.drawable.wide_image_placeholder)
                                    .resize(Const.SQUARE_TARGET_SIZE, Const.SQUARE_TARGET_SIZE)
                                    .into(ivDetailImage)

                            serviceAdapter.setList(dataLaundry.laundryService as ArrayList<LaundryServiceResponse>)

                            //Button Order
                            btDetailOrder.setOnClickListener {
                                //Prevent "jumlah = 0" in the list
                                zeroQtyPreventor()

                                if (serviceOrdered.isNotEmpty()) {
                                    val intent = Intent(this@DetailLaundryActivity, PaymentActivity::class.java)
                                    intent.putExtra(Const.KEY_LAUNDRY_ID, laundryId)
                                    intent.putExtra(PaymentActivity.KEY_SHIPMENT_PRICE, shipmentPrice)
                                    intent.putExtra(Const.KEY_SERVICE_ORDERED, serviceOrdered)
                                    startActivity(intent)
                                } else {
                                    Utils.showToast(this@DetailLaundryActivity, getString(R.string.no_service_selected))
                                }
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    hideView()
                    showMessage(data.message)
                }
            }
        })
    }

    private fun duplicatePreventor(input: LaundryOrderInput) {
        if (serviceOrdered.isEmpty())
            serviceOrdered.add(input)
        else {
            var isNotDuplicate = true
            for (i in 0 until serviceOrdered.size) {
                if (serviceOrdered[i].idLayanan == input.idLayanan) {
                    isNotDuplicate = false
                    serviceOrdered[i] = input
                }
            }
            if (isNotDuplicate)
                serviceOrdered.add(input)
        }
    }

    private fun zeroQtyPreventor() {
        for (i in 0 until serviceOrdered.size) {
            if (serviceOrdered[i].jumlah == 0) {
                serviceOrdered.removeAt(i)
            }
        }
    }

    private fun hideView() {
        with(binding) {
            scrollViewDetailLaundry.visibility = View.INVISIBLE
            btDetailOrder.visibility = View.INVISIBLE
        }
    }

    private fun showView() {
        binding.scrollViewDetailLaundry.visibility = View.VISIBLE
        binding.btDetailOrder.visibility = View.VISIBLE
        binding.constraintViewDetailLaundry.visibility = View.INVISIBLE
        clearStatusInformation()
        Anim.crossFade(binding.constraintViewDetailLaundry)
    }

    private fun showLoading() {
        with(binding.statusDetailLaundry) {
            progressBar.visibility = View.VISIBLE
            tvMessage.visibility = View.INVISIBLE
            tvRetry.visibility = View.INVISIBLE
        }
    }

    private fun showMessage(message: String? = getString(R.string.an_error_occured)) {
        with(binding.statusDetailLaundry) {
            progressBar.visibility = View.INVISIBLE
            tvMessage.visibility = View.VISIBLE
            tvRetry.visibility = View.VISIBLE

            tvMessage.text = message
        }
    }

    private fun clearStatusInformation() {
        with(binding.statusDetailLaundry) {
            progressBar.visibility = View.GONE
            tvMessage.visibility = View.GONE
            tvRetry.visibility = View.GONE
        }
    }
}