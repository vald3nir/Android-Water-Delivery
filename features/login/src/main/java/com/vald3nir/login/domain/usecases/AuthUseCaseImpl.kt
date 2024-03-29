package com.vald3nir.login.domain.usecases

import android.app.Activity
import android.content.Context
import com.vald3nir.core.repository.AuthRepository
import com.vald3nir.core_repository.firebase.FirebaseAuthenticator

class AuthUseCaseImpl(
    private val repository: AuthRepository,
) : AuthUseCase {

    private val authenticator = FirebaseAuthenticator()

    override suspend fun disconnect() {
        authenticator.disconnect()
    }

    override suspend fun getUserID(): String? {
        return authenticator.getUserID()
    }

    override suspend fun checkUserLogged(): Boolean {
        return authenticator.checkUserLogged()
    }

    override suspend fun login(
        activity: Activity?,
        loginDTO: com.vald3nir.core.repository.dtos.LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    ) {
        if (activity != null) {
            repository.login(
                activity = activity,
                loginDTO = loginDTO,
                onSuccess = onSuccess,
                onError = onError
            )
        } else {
            onError.invoke(Exception("Activity null"))
        }
    }

    override suspend fun loadLoginData(context: Context?): com.vald3nir.core.repository.dtos.LoginDTO? {
        return repository.loadLoginData(context)
    }

    override suspend fun saveLoginData(context: Context?, loginDTO: com.vald3nir.core.repository.dtos.LoginDTO) {
        repository.saveLoginData(context, loginDTO)
    }

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
        clientDTO: com.vald3nir.core.repository.dtos.ClientDTO,
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