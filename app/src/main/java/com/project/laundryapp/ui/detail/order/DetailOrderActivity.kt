package com.project.laundryapp.ui.detail.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.databinding.ActivityDetailOrderBinding
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import org.koin.android.ext.android.inject

class DetailOrderActivity : AppCompatActivity() {

    private val viewModel: DetailOrderViewModel by inject()
    private lateinit var binding: ActivityDetailOrderBinding

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

                }

                is Resource.Error -> {
                    Utils.showToast(this, data.message.toString())
                }
            }
        })
    }

    private fun setupUI(historyId: String) {

    }
}