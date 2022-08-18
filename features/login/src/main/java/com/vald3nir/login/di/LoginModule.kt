package com.vald3nir.login.di

import android.content.Context
import com.vald3nir.login.R
import com.vald3nir.login.domain.usecases.AuthUseCase
import com.vald3nir.login.domain.usecases.AuthUseCaseImpl
import com.vald3nir.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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