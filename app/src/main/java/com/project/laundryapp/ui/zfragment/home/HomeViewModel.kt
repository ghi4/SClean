package com.project.laundryapp.ui.zfragment.home

import androidx.lifecycle.*
import com.project.laundryapp.core.data.LaundryRepository

class HomeViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private var triggerCall = MutableLiveData<Unit>()

    var laundryData = triggerCall.switchMap {
        laundryRepository.getLaundryList().asLiveData()
    }

    var promotionData = triggerCall.switchMap {
        laundryRepository.getPromotionList().asLiveData()
    }

    fun triggerCall() {
        triggerCall.postValue(Unit)
    }
}