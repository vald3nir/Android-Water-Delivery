package com.vald3nir.diskwater.domain.use_cases.auth

import android.app.Activity
import android.content.Context
import com.vald3nir.diskwater.data.dto.LoginDTO
import com.vald3nir.diskwater.data.repository.auth.AuthRepository
import com.vald3nir.toolkit.data.repository.remote.firebase.FirebaseAuthenticator

class AuthUseCaseImpl(private val repository: AuthRepository) : AuthUseCase {

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
}