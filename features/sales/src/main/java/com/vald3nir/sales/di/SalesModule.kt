package com.vald3nir.sales.di

import com.vald3nir.sales.domain.use_cases.AddressUseCase
import com.vald3nir.sales.domain.use_cases.AddressUseCaseImpl
import com.vald3nir.sales.presentation.SalesViewModel
import com.vald3nir.sales.repository.AddressRepository
import com.vald3nir.sales.repository.AddressRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun getSalesModule(): Module {
    return module {
        single<AddressRepository> { AddressRepositoryImpl() }
        single<AddressUseCase> { AddressUseCaseImpl(get()) }

        viewModel { SalesViewModel(get(), get(), get()) }
    }
}