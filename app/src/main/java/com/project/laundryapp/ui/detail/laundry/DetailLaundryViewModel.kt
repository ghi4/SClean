package com.project.laundryapp.ui.detail.laundry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.LaundryRepository

class DetailLaundryViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private val triggerCall = MutableLiveData<String>()

    var laundryDetail = triggerCall.switchMap {
        laundryRepository.getLaundryDetail(it).asLiveData()
    }

    fun triggerCall(laundryId: String){
        triggerCall.postValue(laundryId)
    }
}