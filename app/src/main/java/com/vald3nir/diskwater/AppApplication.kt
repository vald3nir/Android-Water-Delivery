package com.vald3nir.diskwater

import android.app.Application
import com.vald3nir.diskwater.data.repository.address.AddressRepository
import com.vald3nir.diskwater.data.repository.address.AddressRepositoryImpl
import com.vald3nir.diskwater.data.repository.auth.AuthRepository
import com.vald3nir.diskwater.data.repository.auth.AuthRepositoryImpl
import com.vald3nir.diskwater.data.repository.product.ProductRepository
import com.vald3nir.diskwater.data.repository.product.ProductRepositoryImpl
import com.vald3nir.diskwater.data.repository.register.RegisterRepository
import com.vald3nir.diskwater.data.repository.register.RegisterRepositoryImpl
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.navigation.ScreenNavigationImpl
import com.vald3nir.diskwater.domain.use_cases.address.AddressUseCase
import com.vald3nir.diskwater.domain.use_cases.address.AddressUseCaseImpl
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCaseImpl
import com.vald3nir.diskwater.domain.use_cases.product.ProductUseCase
import com.vald3nir.diskwater.domain.use_cases.product.ProductUseCaseImpl
import com.vald3nir.diskwater.domain.use_cases.register.RegisterUseCase
import com.vald3nir.diskwater.domain.use_cases.register.RegisterUseCaseImpl
import com.vald3nir.diskwater.presentation.dashboard.DashboardViewModel
import com.vald3nir.diskwater.presentation.login.LoginViewModel
import com.vald3nir.diskwater.presentation.orders.OrderViewModel
import com.vald3nir.diskwater.presentation.product.ProductViewModel
import com.vald3nir.diskwater.presentation.register.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@AppApplication)
            modules(getModules())
        }
    }

    private fun getModules(): Module {

        return module {

            factory<AddressRepository> { AddressRepositoryImpl() }
            factory<AddressUseCase> { AddressUseCaseImpl(get()) }

            factory<ProductUseCase> { ProductUseCaseImpl(get()) }
            factory<ProductRepository> { ProductRepositoryImpl() }

            factory<AuthRepository> { AuthRepositoryImpl() }
            factory<AuthUseCase> { AuthUseCaseImpl(get()) }

            factory<RegisterRepository> { RegisterRepositoryImpl() }
            factory<RegisterUseCase> { RegisterUseCaseImpl(get()) }

            factory<ScreenNavigation> { ScreenNavigationImpl() }

            setupViewModels()
        }
    }

    private fun Module.setupViewModels() {
        viewModel { LoginViewModel(get(), get()) }
        viewModel { RegisterViewModel(get(), get()) }
        viewModel { DashboardViewModel(get(), get()) }
        viewModel { ProductViewModel(get()) }
        viewModel { OrderViewModel(get()) }
    }
}