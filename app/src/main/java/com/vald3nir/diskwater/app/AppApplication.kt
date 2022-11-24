package com.vald3nir.diskwater.app

import android.app.Application
import android.content.Context
import com.vald3nir.diskwater.BuildConfig
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.app.navigation.FeaturesNavigation
import com.vald3nir.diskwater.app.navigation.FeaturesNavigationImpl
import com.vald3nir.diskwater.domain.repository.*
import com.vald3nir.diskwater.domain.usecases.*
import com.vald3nir.diskwater.presentation.features.dashboard.DashboardViewModel
import com.vald3nir.diskwater.presentation.features.dashboard.products.ProductViewModel
import com.vald3nir.diskwater.presentation.features.login.LoginViewModel
import com.vald3nir.diskwater.presentation.features.sales.address.AddressViewModel
import com.vald3nir.diskwater.presentation.features.sales.create.CreateOrderViewModel
import com.vald3nir.diskwater.presentation.features.sales.home.MyOrdersViewModel
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
            modules(
                listOf(
                    appModule(),
                    getRepositoryModule(),
                    getDashboardModule(),
                    getSalesModule(),
                    getLoginModule(
                        context = applicationContext,
                        isClientApp = isAppClient()
                    ),
                )
            )
        }
    }

    private fun appModule(): Module {
        return module {
            single<FeaturesNavigation> { FeaturesNavigationImpl() }
        }
    }

    fun getLoginModule(context: Context, isClientApp: Boolean) = module {

        val clientType = context.getString(if (isClientApp) R.string.client else R.string.salesman)

        single<AuthUseCase> { AuthUseCaseImpl(get()) }

        viewModel {
            LoginViewModel(
                titleScreen = clientType,
                showRegisterButton = isClientApp,
                authUseCase = get()
            )
        }
    }

    private fun getSalesModule() = module {

        single<AddressUseCase> { AddressUseCaseImpl(get()) }
        single<OrderUseCase> { OrderUseCaseImpl(get()) }

        viewModel { MyOrdersViewModel(get()) }
        viewModel { AddressViewModel(get(), get()) }
        viewModel { CreateOrderViewModel(get(), get()) }
    }

    private fun getDashboardModule() = module {

        single<OrderManagerUseCase> { OrderManagerUseCaseImpl() }
        single<ProductUseCase> { ProductUseCaseImpl(get()) }

        viewModel { DashboardViewModel(get()) }
        viewModel { ProductViewModel(get()) }
    }

    private fun getRepositoryModule() = module {
        single<AuthRepository> { AuthRepositoryImpl() }
        single<AddressRepository> { AddressRepositoryImpl() }
        single<OrderRepository> { OrderRepositoryImpl() }
        single<ProductRepository> { ProductRepositoryImpl() }
    }

}