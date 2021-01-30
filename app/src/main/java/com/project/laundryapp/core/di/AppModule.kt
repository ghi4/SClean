package com.project.laundryapp.di

import com.project.laundryapp.core.data.remote.RemoteDataSource
import com.project.laundryapp.core.data.remote.retrofit.RetrofitInterface
import com.project.laundryapp.ui.address.AddressViewModel
import com.project.laundryapp.ui.login.LoginViewModel
import com.project.laundryapp.ui.register.RegisterActivity
import com.project.laundryapp.ui.register.RegisterViewModel
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
}

val viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { AddressViewModel(get()) }
}
