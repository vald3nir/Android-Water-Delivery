package com.vald3nir.diskwater.domain.use_cases.register

import android.app.Activity
import com.vald3nir.diskwater.data.dto.ClientDTO
import com.vald3nir.diskwater.data.repository.register.RegisterRepository

class RegisterUseCaseImpl(
    private val repository: RegisterRepository
) : RegisterUseCase {

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

    override suspend fun registerClient(
        activity: Activity?,
        clientDTO: ClientDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        activity?.let {
            repository.registerClient(
                activity = it,
                clientDTO = clientDTO,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}