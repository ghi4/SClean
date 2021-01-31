package com.project.laundryapp.ui.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundryHistoryAdapter
import com.project.laundryapp.core.data.local.LaundryOrder
import com.project.laundryapp.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: LaundryHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        historyAdapter = LaundryHistoryAdapter()

        val dummyData = ArrayList<LaundryOrder>()
        for(i in 1..10){
            dummyData.add(
                LaundryOrder(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                        "",
                "",
                "",
            )
            )
        }

        historyAdapter.setList(dummyData)

        with(binding) {
            rvHistoryLaundry.layoutManager = LinearLayoutManager(context)
            rvHistoryLaundry.hasFixedSize()
            rvHistoryLaundry.adapter = historyAdapter
            rvHistoryLaundry.isNestedScrollingEnabled = false
        }
    }
}