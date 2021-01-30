package com.project.laundryapp.ui.ui.laundry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.laundryapp.R

class LaundryFragment : Fragment() {

    private lateinit var laundryViewModel: LaundryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_laundry, container, false)
        return root
    }
}