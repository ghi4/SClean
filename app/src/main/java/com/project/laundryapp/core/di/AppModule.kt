package com.project.laundryapp.core.di

import com.project.laundryapp.core.data.LaundryRepository
import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.retrofit.RetrofitInterface
import com.project.laundryapp.ui.address.AddressViewModel
import com.project.laundryapp.ui.detail.laundry.DetailLaundryViewModel
import com.project.laundryapp.ui.login.LoginViewModel
import com.project.laundryapp.ui.register.RegisterViewModel
import com.project.laundryapp.ui.ui.history.HistoryViewModel
import com.project.laundryapp.ui.ui.home.HomeViewModel
import com.project.laundryapp.ui.ui.laundry.LaundryViewModel
import com.project.laundryapp.ui.ui.profile.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://testingsclean.000webhostapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RetrofitInterface::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LaundryRepository(get()) }
}

val viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { LaundryViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { DetailLaundryViewModel(get()) }
}
