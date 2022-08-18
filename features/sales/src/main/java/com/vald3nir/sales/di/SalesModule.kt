package com.vald3nir.sales.di

import com.vald3nir.sales.domain.use_cases.AddressUseCase
import com.vald3nir.sales.domain.use_cases.AddressUseCaseImpl
import com.vald3nir.sales.domain.use_cases.OrderUseCase
import com.vald3nir.sales.domain.use_cases.OrderUseCaseImpl
import com.vald3nir.sales.presentation.address.AddressViewModel
import com.vald3nir.sales.presentation.create.CreateOrderViewModel
import com.vald3nir.sales.presentation.home.MyOrdersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getSalesModule() = module {

    single<AddressUseCase> { AddressUseCaseImpl(get()) }
    single<OrderUseCase> { OrderUseCaseImpl(get()) }

    viewModel { MyOrdersViewModel(get()) }
    viewModel { AddressViewModel(get(), get()) }
    viewModel { CreateOrderViewModel(get(), get()) }
}