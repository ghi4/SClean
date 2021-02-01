package com.project.laundryapp.ui.detail.laundry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.LaundryRepository
import com.project.laundryapp.core.data.remote.response.laundry.LaundryServiceResponse

class DetailLaundryViewModel(private val laundryRepository: LaundryRepository) : ViewModel() {

    private val triggerCall = MutableLiveData<String>()
    private var triggerPostData = MutableLiveData<LaundryServiceResponse>()

    var laundryDetail = triggerCall.switchMap {
        laundryRepository.getLaundryDetail(it).asLiveData()
    }

//    var postDataStatus = triggerPostData.switchMap {
////        laundryRepository.postOrder(
////            it.idLaundry,
////            it.id
////        ).asLiveData()
//    }

    fun triggerCall(laundryId: String){
        triggerCall.postValue(laundryId)
    }

    fun triggerPostData(data: LaundryServiceResponse) {
        triggerPostData.postValue(data)
    }
}