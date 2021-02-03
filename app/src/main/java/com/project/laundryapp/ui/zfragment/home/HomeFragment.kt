package com.project.laundryapp.ui.zfragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.core.adapter.BannerAdapter
import com.project.laundryapp.core.adapter.LaundryTopAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.core.data.remote.response.laundry.LaundryDataResponse
import com.project.laundryapp.core.data.remote.response.promotion.PromotionResponse
import com.project.laundryapp.databinding.FragmentHomeBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.detail.laundry.DetailLaundryActivity
import com.project.laundryapp.utils.Anim
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
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

    private fun setupUI() {
        hideView()
        MainActivity.showLoading()

        //Adapter
        bannerAdapter = BannerAdapter()
        laundryTopAdapter = LaundryTopAdapter()

        //Binding
        with(binding) {
            rvHomeRecommendation.layoutManager = LinearLayoutManager(context)
            rvHomeRecommendation.hasFixedSize()
            rvHomeRecommendation.adapter = laundryTopAdapter
            rvHomeRecommendation.isNestedScrollingEnabled = false

            vpHomeBanner.adapter = bannerAdapter
            dotsIndicatorBanner.setViewPager2(vpHomeBanner)
        }

        //When list data clicked
        laundryTopAdapter.onItemClick = {selectedData ->
            val intent = Intent(requireContext(), DetailLaundryActivity::class.java)
            intent.putExtra(Const.KEY_LAUNDRY_ID, selectedData.idLaundry)
            startActivity(intent)
        }
    }

    private fun getData() {
        viewModel.laundryData.observe(viewLifecycleOwner, {data ->
            when(data) {
                is Resource.Loading -> {
                    hideView()
                    MainActivity.showLoading()
                }

                is Resource.Success -> {
                    val dataStatus = data.data
                    val dataLaundry = dataStatus?.data

                    laundryTopAdapter.setList(dataLaundry as ArrayList<LaundryDataResponse>)

                    showView()
                }

                is Resource.Error -> {
                    hideView()
                    MainActivity.showMessage(data.data?.message ?: data.message)
                }
            }
        })

        viewModel.promotionData.observe(viewLifecycleOwner, {data->
            when(data) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    val promotionList = data.data?.data
                    bannerAdapter.setList(promotionList as ArrayList<PromotionResponse>)
                }

                is Resource.Error -> {

                }
            }
        })
    }

    private fun hideView() {
        with(binding){
            scrollViewHome.visibility = View.INVISIBLE
        }
    }

    private fun showView() {
        MainActivity.clearStatusInformation()
        Anim.crossFade(binding.scrollViewHome)
    }
}