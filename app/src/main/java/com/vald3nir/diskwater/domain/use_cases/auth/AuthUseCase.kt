package com.vald3nir.diskwater.domain.use_cases.auth

import com.vald3nir.toolkit.core.AppView
import com.vald3nir.diskwater.data.dto.LoginDTO

interface AuthUseCase {

    suspend fun disconnect()

    suspend fun checkUserLogged(): Boolean

    suspend fun getUserID(): String?

    suspend fun login(
        appView: AppView?,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun loadLoginData(
        onSuccess: (loginDTO: LoginDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun saveLoginData(loginDTO: LoginDTO, onSuccess: () -> Unit)
}