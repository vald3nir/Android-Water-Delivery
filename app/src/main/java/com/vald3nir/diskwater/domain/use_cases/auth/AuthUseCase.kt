package com.vald3nir.diskwater.domain.use_cases.auth

import android.app.Activity
import android.content.Context
import com.vald3nir.diskwater.data.dto.LoginDTO

interface AuthUseCase {

    suspend fun disconnect()

    suspend fun checkUserLogged(): Boolean

    suspend fun getUserID(): String?

    suspend fun login(
        activity: Activity?,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun loadLoginData(context: Context?): LoginDTO?

    suspend fun saveLoginData(context: Context?, loginDTO: LoginDTO)
}