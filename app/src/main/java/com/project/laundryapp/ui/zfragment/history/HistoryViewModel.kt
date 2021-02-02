package com.project.laundryapp.ui.zfragment.history

import androidx.lifecycle.*
import com.project.laundryapp.core.data.LaundryRepository

class HistoryViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private val triggerCall = MutableLiveData<String>()

    var dataHistory = triggerCall.switchMap {
        laundryRepository.getLaundryHistory(it).asLiveData()
    }

    fun triggerCall(userId: String) {
        triggerCall.postValue(userId)
    }

}