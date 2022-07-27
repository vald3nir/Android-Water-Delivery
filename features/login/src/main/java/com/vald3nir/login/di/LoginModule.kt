package com.vald3nir.login.di

import android.content.Context
import com.vald3nir.login.R
import com.vald3nir.login.domain.usecases.AuthUseCase
import com.vald3nir.login.domain.usecases.AuthUseCaseImpl
import com.vald3nir.login.presentation.LoginViewModel
import com.vald3nir.login.repository.AuthRepository
import com.vald3nir.login.repository.AuthRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun getLoginModule(
    context: Context,
    isClientApp: Boolean
): Module {
    return module {

        single<AuthRepository> { AuthRepositoryImpl() }

        single<AuthUseCase> { AuthUseCaseImpl(get()) }

        viewModel {
            LoginViewModel(
                titleScreen = context.getString(if (isClientApp) R.string.client else R.string.salesman),
                showRegisterButton = isClientApp,
                authUseCase = get()
            )
        }
    }
}