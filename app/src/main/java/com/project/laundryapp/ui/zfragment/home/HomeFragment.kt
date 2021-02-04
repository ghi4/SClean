package com.project.laundryapp.ui.zfragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.BannerAdapter
import com.project.laundryapp.core.adapter.LaundryTopAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryDataResponse
import com.project.laundryapp.core.data.remote.response.promotion.PromotionResponse
import com.project.laundryapp.databinding.FragmentHomeBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.detail.laundry.DetailLaundryActivity
import com.project.laundryapp.utils.Anim
import com.project.laundryapp.utils.Const
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.concurrent.timerTask

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by inject()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var laundryTopAdapter: LaundryTopAdapter

    private var banner = 0
    private var delayBanner = 500L
    private var durationBanner = 3000L

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

        //Adapter
        bannerAdapter = BannerAdapter(requireContext())
        laundryTopAdapter = LaundryTopAdapter(requireContext())

        //Binding
        with(binding) {
            rvHomeRecommendation.layoutManager = LinearLayoutManager(context)
            rvHomeRecommendation.hasFixedSize()
            rvHomeRecommendation.adapter = laundryTopAdapter
            rvHomeRecommendation.isNestedScrollingEnabled = false

            vpHomeBanner.adapter = bannerAdapter
            dotsIndicatorBanner.setViewPager2(vpHomeBanner)
        }

        //Item list click
        laundryTopAdapter.onItemClick = { selectedData ->
            val intent = Intent(requireContext(), DetailLaundryActivity::class.java)
            intent.putExtra(Const.KEY_LAUNDRY_ID, selectedData.idLaundry)
            startActivity(intent)
        }

        //Button Retry
        MainActivity.getStatusView().tvRetry.setOnClickListener {
            viewModel.triggerCall()
        }
    }

    private fun getData() {
        viewModel.laundryData.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resource.Loading -> {
                    hideView()
                    MainActivity.showLoading()
                }

                is Resource.Success -> {
                    val dataList = data.data?.data as ArrayList<LaundryDataResponse>

                    if (dataList.isNotEmpty()) {
                        showView()
                        laundryTopAdapter.setList(dataList)
                    } else {
                        hideView()
                        MainActivity.showMessage(getString(R.string.no_laundry_nearby))
                        MainActivity.getStatusView().tvRetry.visibility = View.INVISIBLE
                    }
                }

                is Resource.Error -> {
                    hideView()
                    MainActivity.showMessage(data.message)
                }
            }
        })

        viewModel.promotionData.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resource.Loading -> {
                    //Done by "viewModel.laundryData"
                }

                is Resource.Success -> {
                    val promotionList = data.data?.data
                    bannerAdapter.setList(promotionList as ArrayList<PromotionResponse>)

                    //Banner auto swipe
                    Timer().schedule(timerTask {
                        activity?.runOnUiThread {
                            if (banner == promotionList.size)
                                banner = 0
                            binding.vpHomeBanner.setCurrentItem(banner++, true)
                        }
                    }, delayBanner, durationBanner)
                }

                is Resource.Error -> {
                    //Done by "viewModel.laundryData"
                }
            }
        })
    }

    private fun hideView() {
        with(binding) {
            scrollViewHome.visibility = View.INVISIBLE
        }
    }

    private fun showView() {
        MainActivity.clearStatusInformation()
        Anim.crossFade(binding.scrollViewHome)
    }
}