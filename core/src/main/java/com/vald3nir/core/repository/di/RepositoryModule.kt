package com.vald3nir.core.repository.di

import com.vald3nir.core.repository.*
import org.koin.dsl.module

fun getRepositoryModule() = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single<AddressRepository> { AddressRepositoryImpl() }
    single<OrderRepository> { OrderRepositoryImpl() }
    single<ProductRepository> { ProductRepositoryImpl() }
}