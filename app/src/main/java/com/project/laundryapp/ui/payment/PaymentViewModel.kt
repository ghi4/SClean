package com.project.laundryapp.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.project.laundryapp.core.data.LaundryRepository
import com.project.laundryapp.core.data.remote.response.laundry.LaundryOrderInput
import com.project.laundryapp.core.data.remote.response.laundry.LaundryServiceResponse

class PaymentViewModel(private val laundryRepository: LaundryRepository): ViewModel() {

    private val triggerPayment = MutableLiveData<LaundryServiceResponse>()

    var orderResponse = triggerPayment.switchMap {
        laundryRepository.postOrder(
            it.idLaundry.toString(),
            it.idLayanan.toString(),
            (it.daftarLayanan ?: ArrayList()) as ArrayList<LaundryOrderInput>
        ).asLiveData()
    }

    fun triggerPayment(data: LaundryServiceResponse) {
        triggerPayment.postValue(data)
    }
}