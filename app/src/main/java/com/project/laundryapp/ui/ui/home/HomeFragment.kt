package com.project.laundryapp.ui.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.BannerAdapter
import com.project.laundryapp.core.adapter.LaundryTopAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.core.data.remote.ApiResponse
import com.project.laundryapp.core.data.remote.response.LaundryDataResponse
import com.project.laundryapp.databinding.FragmentHomeBinding
import com.project.laundryapp.ui.detail.laundry.DetailLaundryActivity
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by inject()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var laundryTopAdapter: LaundryTopAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.triggerCall()
        setupUI()
        getData()
    }

    private fun getData() {
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

                    laundryTopAdapter.setList(dataLaundry as ArrayList<LaundryDataResponse>)

                }

                is Resource.Error -> {
                    Log.d("HHWZ", data.message.toString() )
                }
            }
        })
    }

    private fun setupUI() {
        bannerAdapter = BannerAdapter()
        laundryTopAdapter = LaundryTopAdapter()

        val dummy = ArrayList<Laundry>()
        for (i in 1..10) {
            dummy.add(Laundry(
                "AAA",
                "TOKO A",
                "62887",
                "08:00",
                "20:00,",
                "Jl Raya di Jakarta",
                "Lorem ipsum",
                "abc"
            ))
        }

        bannerAdapter.setList(dummy)

        laundryTopAdapter.onItemClick = {selectedData ->
            val intent = Intent(requireContext(), DetailLaundryActivity::class.java)
            Log.d("HHWZ", "HOME: ${selectedData.idLaundry}")
            intent.putExtra("KEY_INI", selectedData.idLaundry)
            startActivity(intent)
        }

        with(binding) {
            rvHomeRecommendation.layoutManager = LinearLayoutManager(context)
            rvHomeRecommendation.hasFixedSize()
            rvHomeRecommendation.adapter = laundryTopAdapter
            rvHomeRecommendation.isNestedScrollingEnabled = false

            vpHomeBanner.adapter = bannerAdapter
        }
    }
}