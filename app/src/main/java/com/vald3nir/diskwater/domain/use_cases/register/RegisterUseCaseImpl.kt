package com.vald3nir.diskwater.domain.use_cases.register

import com.vald3nir.diskwater.common.core.AppView
import com.vald3nir.diskwater.data.repository.remote.register.RegisterRepository

class RegisterUseCaseImpl(private val repository: RegisterRepository) : RegisterUseCase {

    override suspend fun registerNewUser(
        appView: AppView?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        appView?.getActivityContext()?.let { activity ->
            repository.registerNewUser(
                activity = activity,
                email = email,
                password = password,
                onSuccess = onSuccess,
                onError = onError,
            )
        }
    }

    override suspend fun registerUserType(
        appView: AppView?,
        userID: String,
        isSalesman: Boolean,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        appView?.getActivityContext()?.let { activity ->
            repository.registerUserType(
                activity = activity,
                isSalesman = isSalesman,
                userID = userID,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}