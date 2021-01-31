package com.project.laundryapp.ui.ui.laundry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundrySideAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.core.data.remote.response.LaundryDataResponse
import com.project.laundryapp.databinding.FragmentLaundryBinding
import org.koin.android.ext.android.inject

class LaundryFragment : Fragment() {

    private val viewModel: LaundryViewModel by inject()
    private lateinit var binding: FragmentLaundryBinding
    private lateinit var laundrySideAdapter: LaundrySideAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaundryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        getData()
    }

    private fun getData() {
        viewModel.triggerCall()
        viewModel.laundryData.observe(viewLifecycleOwner, {data ->
            Log.d("HHWZ", "" + data.toString())
            when(data) {
                is Resource.Loading -> {
                    Log.d("HHWZ", "ON LOADING")
                }

                is Resource.Success -> {
                    val dataStatus = data.data
                    val dataLaundry = dataStatus?.data

                    Log.d("HHWZ", "AAA: $data")
                    Log.d("HHWZ", "BBB: " + dataStatus.toString())
                    Log.d("HHWZ", "CCC: " + dataLaundry.toString())

                    laundrySideAdapter.setList(dataLaundry as ArrayList<LaundryDataResponse>)

                }

                is Resource.Error -> {
                    Log.d("HHWZ", data.message.toString())
                }
            }
        })

    }

    private fun setupUI() {
        laundrySideAdapter = LaundrySideAdapter()

        val dummy = ArrayList<Laundry>()
        for (i in 1..10) {
            dummy.add(
                Laundry(
                "AAA",
                "TOKO A",
                "62887",
                "08:00",
                "20:00,",
                "Jl Raya di Jakarta",
                "Lorem ipsum",
                "abc"
                )
            )
        }

        with(binding) {
            rvLaundry.layoutManager = LinearLayoutManager(context)
            rvLaundry.hasFixedSize()
            rvLaundry.adapter = laundrySideAdapter
            rvLaundry.isNestedScrollingEnabled = false
        }
    }
}