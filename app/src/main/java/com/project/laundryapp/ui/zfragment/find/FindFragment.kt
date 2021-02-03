package com.project.laundryapp.ui.zfragment.find

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.core.adapter.LaundrySideAdapter
import com.project.laundryapp.core.data.Resource
import com.project.laundryapp.core.data.remote.response.laundry.LaundryDataResponse
import com.project.laundryapp.databinding.FragmentLaundryBinding
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.detail.laundry.DetailLaundryActivity
import com.project.laundryapp.utils.Anim
import com.project.laundryapp.utils.Const
import org.koin.android.ext.android.inject

class FindFragment : Fragment() {

    private val viewModel: FindViewModel by inject()
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

        viewModel.triggerCall()
        setupUI()
        getData()
    }

    private fun setupUI() {
        laundrySideAdapter = LaundrySideAdapter()

        with(binding) {
            hideView()

            rvLaundry.layoutManager = LinearLayoutManager(context)
            rvLaundry.hasFixedSize()
            rvLaundry.adapter = laundrySideAdapter
            rvLaundry.isNestedScrollingEnabled = false
        }

        //OnClick Listener
        laundrySideAdapter.onItemClick = {selectedData ->
            val intent = Intent(requireContext(), DetailLaundryActivity::class.java)
            intent.putExtra(Const.KEY_LAUNDRY_ID, selectedData.idLaundry)
            startActivity(intent)
        }

        MainActivity.getListener().tvRetry.setOnClickListener {
            viewModel.triggerCall()
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
                    val laundryList = data.data?.data as ArrayList<LaundryDataResponse>

                    if(laundryList.isNotEmpty()) {
                        showView()
                        laundrySideAdapter.setList(laundryList)
                    } else {
                        hideView()
                        MainActivity.showMessage("Tidak ada laundry di dekat sini.")
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
        with(binding){
            constrainViewFind.visibility = View.INVISIBLE
        }
    }

    private fun showView() {
        MainActivity.clearStatusInformation()
        Anim.crossFade(binding.constrainViewFind)
    }
}