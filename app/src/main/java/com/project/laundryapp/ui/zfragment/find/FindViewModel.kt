package com.project.laundryapp.ui.zfragment.find

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.LaundryRepository

class FindViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private var triggerCall = MutableLiveData<Unit>()

    var laundryData = triggerCall.switchMap {
        laundryRepository.getLaundryList().asLiveData()
    }

    fun triggerCall() {
        triggerCall.postValue(Unit)
    }
}