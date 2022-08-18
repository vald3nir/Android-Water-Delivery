package com.vald3nir.dashboard.di

import com.vald3nir.dashboard.domain.OrderManagerUseCase
import com.vald3nir.dashboard.domain.OrderManagerUseCaseImpl
import com.vald3nir.dashboard.domain.ProductUseCase
import com.vald3nir.dashboard.domain.ProductUseCaseImpl
import com.vald3nir.dashboard.presentation.orders.DashboardViewModel
import com.vald3nir.dashboard.presentation.products.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getDashboardModule() = module {

    single<OrderManagerUseCase> { OrderManagerUseCaseImpl() }
    single<ProductUseCase> { ProductUseCaseImpl(get()) }

    viewModel { DashboardViewModel(get()) }
    viewModel { ProductViewModel(get()) }
}