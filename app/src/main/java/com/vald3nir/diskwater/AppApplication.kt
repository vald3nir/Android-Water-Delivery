package com.vald3nir.diskwater

import android.app.Application
import com.vald3nir.core.presentation.FeaturesNavigation
import com.vald3nir.core.repository.di.getRepositoryModule
import com.vald3nir.dashboard.di.getDashboardModule
import com.vald3nir.login.di.getLoginModule
import com.vald3nir.sales.di.getSalesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
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
}