package com.project.laundryapp.ui.zfragment.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundryHistoryAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryHistoryResponse
import com.project.laundryapp.databinding.FragmentHistoryBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.detail.order.DetailOrderActivity
import com.project.laundryapp.utils.Anim
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModel()
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

        if (savedInstanceState == null) {
            val user = Utils.getSharedPref(requireActivity())
            viewModel.triggerCall(user.id.toString())
        }

        setupUI()
        getData()
    }

    private fun setupUI() {
        hideView()

        //Adapter
        historyAdapter = LaundryHistoryAdapter()

        //Binding
        with(binding) {
            rvHistoryLaundry.layoutManager = LinearLayoutManager(context)
            rvHistoryLaundry.hasFixedSize()
            rvHistoryLaundry.adapter = historyAdapter
            rvHistoryLaundry.isNestedScrollingEnabled = false
        }

        //Item list click
        historyAdapter.onItemClick = { selectedData ->
            val intent = Intent(requireContext(), DetailOrderActivity::class.java)
            intent.putExtra(Const.KEY_LAUNDRY_HISTORY_ID, selectedData.idPesanan)
            startActivity(intent)
        }

        //Button Retry
        MainActivity.getStatusView().tvRetry.setOnClickListener {
            val user = Utils.getSharedPref(requireActivity())
            viewModel.triggerCall(user.id.toString())
        }

        //Button Refresh
        binding.btHistoryRefresh.setOnClickListener {
            val user = Utils.getSharedPref(requireActivity())
            viewModel.triggerCall(user.id.toString())
        }
    }

    private fun getData() {
        viewModel.dataHistory.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resource.Loading -> {
                    hideView()
                    MainActivity.showLoading()
                }

                is Resource.Success -> {
                    val historyList = data.data?.data as ArrayList<LaundryHistoryResponse>

                    if (historyList.isNotEmpty()) {
                        showView()
                        historyAdapter.setList(historyList)
                    } else {
                        hideView()
                        MainActivity.showMessage(getString(R.string.no_order_history))
                        MainActivity.getStatusView().tvRetry.visibility = View.INVISIBLE
                    }
                }

                is Resource.Error -> {
                    hideView()
                    MainActivity.showMessage(data.message)
                }
            }
        })
    }

    private fun hideView() {
        with(binding) {
            constrainViewHistory.visibility = View.INVISIBLE
        }
    }

    private fun showView() {
        MainActivity.clearStatusInformation()
        Anim.crossFade(binding.constrainViewHistory)
    }
}