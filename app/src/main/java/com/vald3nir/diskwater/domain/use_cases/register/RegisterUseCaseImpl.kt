package com.vald3nir.diskwater.domain.use_cases.register

import android.app.Activity
import com.vald3nir.toolkit.core.ViewModelController
import com.vald3nir.diskwater.data.repository.register.RegisterRepository

class RegisterUseCaseImpl(private val repository: RegisterRepository) : RegisterUseCase {

    override suspend fun registerNewUser(
        activity: Activity?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        activity?.let {
            repository.registerNewUser(
                activity = it,
                email = email,
                password = password,
                onSuccess = onSuccess,
                onError = onError,
            )
        }
    }

    override suspend fun registerUserType(
        activity: Activity?,
        userID: String,
        isSalesman: Boolean,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        activity?.let {
            repository.registerUserType(
                activity = it,
                isSalesman = isSalesman,
                userID = userID,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}