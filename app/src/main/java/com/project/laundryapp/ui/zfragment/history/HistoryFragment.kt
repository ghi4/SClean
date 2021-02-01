package com.project.laundryapp.ui.zfragment.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.core.adapter.LaundryHistoryAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryHistoryResponse
import com.project.laundryapp.databinding.FragmentHistoryBinding
import com.project.laundryapp.ui.detail.order.DetailOrderActivity
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import org.koin.android.ext.android.inject

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by inject()
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: LaundryHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = Utils.getSharedPref(requireActivity())
        viewModel.triggerCall(user.id ?: "")

        setupUI()
        getData()
    }

    private fun getData() {
        viewModel.dataHistory.observe(viewLifecycleOwner, {data ->
            Log.d("HISTORY TAG", """
                    BASE:
                    $data
                    
                    MESSAGE:
                    ${data.message}
                    
                    MESSAGE RESPONSE:
                    ${data.data?.message}
                                        
                    MESSAGE ERROR
                    ${data.data?.error}
                    
                    RESOURCE:
                    ${data.data}
                    
                    DATA LIST:
                    ${data.data?.data}
                """.trimIndent())

            when(data) {
                is Resource.Loading -> {
                    Utils.showToast(requireContext(), "Memuat data.")
                }

                is Resource.Success -> {
                    val historyList = data.data?.data
                    historyAdapter.setList(historyList as ArrayList<LaundryHistoryResponse>)
                }

                is Resource.Error -> {
                    Utils.showToast(requireContext(), data.message.toString())
                }
            }
        })
    }

    private fun setupUI() {
        historyAdapter = LaundryHistoryAdapter()

        with(binding) {
            rvHistoryLaundry.layoutManager = LinearLayoutManager(context)
            rvHistoryLaundry.hasFixedSize()
            rvHistoryLaundry.adapter = historyAdapter
            rvHistoryLaundry.isNestedScrollingEnabled = false
        }

        historyAdapter.onItemClick = {selectedData ->
            val intent = Intent(requireContext(), DetailOrderActivity::class.java)
            intent.putExtra(Const.KEY_LAUNDRY_HISTORY_ID, selectedData.idPesanan)
            startActivity(intent)
        }
    }
}