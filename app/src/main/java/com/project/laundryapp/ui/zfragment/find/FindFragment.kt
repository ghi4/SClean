package com.project.laundryapp.ui.zfragment.find

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.project.laundryapp.utils.Utils
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

    private fun getData() {
        viewModel.laundryData.observe(viewLifecycleOwner, {data ->
            Log.d("FIND TAG", """
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
                    MainActivity.showLoading()
                }

                is Resource.Success -> {
                    val dataStatus = data.data
                    val dataLaundry = dataStatus?.data

                    laundrySideAdapter.setList(dataLaundry as ArrayList<LaundryDataResponse>)

                    MainActivity.clearStatusInformation()
                    Anim.crossFade(binding.root)
                }

                is Resource.Error -> {
                    Utils.showToast(requireContext(), data.message.toString())
                }
            }
        })

    }

    private fun setupUI() {
        laundrySideAdapter = LaundrySideAdapter()

        with(binding) {
            root.visibility = View.INVISIBLE

            rvLaundry.layoutManager = LinearLayoutManager(context)
            rvLaundry.hasFixedSize()
            rvLaundry.adapter = laundrySideAdapter
            rvLaundry.isNestedScrollingEnabled = false
        }

        laundrySideAdapter.onItemClick = {selectedData ->
            val intent = Intent(requireContext(), DetailLaundryActivity::class.java)
            intent.putExtra(Const.KEY_LAUNDRY_ID, selectedData.idLaundry)
            startActivity(intent)
        }
    }
}