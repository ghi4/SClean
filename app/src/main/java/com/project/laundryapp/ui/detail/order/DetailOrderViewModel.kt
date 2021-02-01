package com.project.laundryapp.ui.detail.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.LaundryRepository

class DetailOrderViewModel(private val laundryRepository: LaundryRepository) : ViewModel(){

    private val triggerCall = MutableLiveData<String>()
    var historyDetail = triggerCall.switchMap {
        laundryRepository.getLaundryHistoryDetailByHistoryId(it).asLiveData()
    }

    fun triggerCall(historyId: String) {
        triggerCall.postValue(historyId)
    }
}