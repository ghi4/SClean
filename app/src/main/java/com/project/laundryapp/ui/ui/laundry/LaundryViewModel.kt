package com.project.laundryapp.ui.ui.laundry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.laundryapp.core.data.LaundryRepository

class LaundryViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}