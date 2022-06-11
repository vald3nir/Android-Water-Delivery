package com.vald3nir.diskwater.domain.use_cases.auth

import android.app.Activity
import android.content.Context
import com.vald3nir.diskwater.data.dto.LoginDTO
import com.vald3nir.diskwater.data.repository.auth.AuthRepository
import com.vald3nir.toolkit.data.repository.remote.loadCurrentUser

class AuthUseCaseImpl(private val repository: AuthRepository) : AuthUseCase {

    override suspend fun disconnect() {
    }

    override suspend fun getUserID(): String? {
        return loadCurrentUser()?.uid
    }

    override suspend fun checkUserLogged(): Boolean {
        return loadCurrentUser() != null
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