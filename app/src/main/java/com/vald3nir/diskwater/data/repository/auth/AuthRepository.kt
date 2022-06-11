package com.vald3nir.diskwater.data.repository.auth

import android.app.Activity
import android.content.Context
import com.vald3nir.diskwater.data.dto.LoginDTO

interface AuthRepository {

    suspend fun login(
        activity: Activity,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun loadLoginData(context: Context?): LoginDTO?

    suspend fun saveLoginData(context: Context?, loginDTO: LoginDTO)
}