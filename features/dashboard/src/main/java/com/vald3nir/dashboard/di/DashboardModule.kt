package com.vald3nir.dashboard.di

import com.vald3nir.dashboard.domain.ProductUseCase
import com.vald3nir.dashboard.domain.ProductUseCaseImpl
import com.vald3nir.dashboard.presentation.DashboardViewModel
import com.vald3nir.dashboard.presentation.products.ProductViewModel
import com.vald3nir.dashboard.repository.ProductRepository
import com.vald3nir.dashboard.repository.ProductRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun getDashboardModule(): Module {
    return module {

        single<ProductUseCase> { ProductUseCaseImpl(get()) }

        single<ProductRepository> { ProductRepositoryImpl() }

        viewModel { DashboardViewModel() }
        viewModel { ProductViewModel(get()) }
    }
}