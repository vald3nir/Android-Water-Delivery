package com.vald3nir.login.domain.usecases

import android.app.Activity
import android.content.Context
import com.vald3nir.commom.domain.dtos.ClientDTO
import com.vald3nir.commom.domain.dtos.LoginDTO
import com.vald3nir.login.repository.AuthRepository
import com.vald3nir.repository.firebase.FirebaseAuthenticator

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
        loginDTO: LoginDTO,
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

    override suspend fun loadLoginData(context: Context?): LoginDTO? {
        return repository.loadLoginData(context)
    }

    override suspend fun saveLoginData(context: Context?, loginDTO: LoginDTO) {
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