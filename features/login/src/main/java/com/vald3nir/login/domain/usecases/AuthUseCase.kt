package com.vald3nir.login.domain.usecases

import android.app.Activity
import android.content.Context
import com.vald3nir.repository.dtos.ClientDTO
import com.vald3nir.repository.dtos.LoginDTO

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

    suspend fun loadLoginData(context: Context?): com.vald3nir.repository.dtos.LoginDTO?

    suspend fun saveLoginData(context: Context?, loginDTO: com.vald3nir.repository.dtos.LoginDTO)

    suspend fun registerNewUser(
        activity: Activity?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun registerClient(
        activity: Activity?,
        clientDTO: com.vald3nir.repository.dtos.ClientDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}