package com.project.laundryapp.ui.ui.laundry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.laundryapp.R
import com.project.laundryapp.core.adapter.LaundrySideAdapter
import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.databinding.FragmentLaundryBinding

class LaundryFragment : Fragment() {

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

        laundrySideAdapter.setList(dummy)

        with(binding) {
            rvLaundry.layoutManager = LinearLayoutManager(context)
            rvLaundry.hasFixedSize()
            rvLaundry.adapter = laundrySideAdapter
            rvLaundry.isNestedScrollingEnabled = false
        }
    }
}