package com.vald3nir.sales.di

import com.vald3nir.sales.domain.use_cases.AddressUseCase
import com.vald3nir.sales.domain.use_cases.AddressUseCaseImpl
import com.vald3nir.sales.domain.use_cases.OrderUseCase
import com.vald3nir.sales.domain.use_cases.OrderUseCaseImpl
import com.vald3nir.sales.presentation.address.AddressViewModel
import com.vald3nir.sales.presentation.create.CreateOrderViewModel
import com.vald3nir.sales.presentation.home.MyOrdersViewModel
import com.vald3nir.sales.repository.AddressRepository
import com.vald3nir.sales.repository.AddressRepositoryImpl
import com.vald3nir.sales.repository.OrderRepository
import com.vald3nir.sales.repository.OrderRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun getSalesModule(): Module {
    return module {

        single<AddressUseCase> { AddressUseCaseImpl(get()) }
        single<AddressRepository> { AddressRepositoryImpl() }

        single<OrderUseCase> { OrderUseCaseImpl(get()) }
        single<OrderRepository> { OrderRepositoryImpl() }

        viewModel { MyOrdersViewModel(get()) }
        viewModel { AddressViewModel(get(), get()) }
        viewModel { CreateOrderViewModel(get(), get()) }
    }
}