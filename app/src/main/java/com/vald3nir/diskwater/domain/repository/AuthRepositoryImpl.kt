package com.vald3nir.diskwater.domain.repository

import android.app.Activity
import android.content.Context
import com.vald3nir.core_repository.firebase.FirebaseAuthenticator
import com.vald3nir.core_repository.firebase.FirebaseClient
import com.vald3nir.core_repository.storage.loadDataString
import com.vald3nir.core_repository.storage.saveDataString
import com.vald3nir.core_repository.toDTO
import com.vald3nir.diskwater.domain.dtos.ClientDTO
import com.vald3nir.diskwater.domain.dtos.LoginDTO

class AuthRepositoryImpl : AuthRepository {
    private val authenticator = FirebaseAuthenticator()

    override suspend fun login(
        activity: Activity,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val email: String = loginDTO.email ?: ""
        val password: String = loginDTO.password ?: ""
        authenticator.signInWithEmailAndPassword(activity, email, password, onSuccess, onError)
    }

    override suspend fun saveLoginData(
        context: Context?,
        loginDTO: LoginDTO
    ) {
        if (loginDTO.rememberLogin) {
            context?.saveDataString("Login", loginDTO.toJson())
        } else {
            context?.saveDataString("Login", null)
        }
    }

    override suspend fun loadLoginData(context: Context?): LoginDTO {
        val json = context?.loadDataString("Login")
        return json.toDTO(LoginDTO::class.java)
    }

    override suspend fun registerNewUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val firebaseAuthenticator = FirebaseAuthenticator()
        firebaseAuthenticator.createUserWithEmailAndPassword(
            activity = activity,
            email = email,
            password = password,
            onSuccess = onSuccess,
            onError = onError,
        )
    }

    override suspend fun registerClient(
        activity: Activity,
        clientDTO: ClientDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        FirebaseClient().insertOrUpdateData(
            rootPath = "debug",
            document = "usuários",
            collection = "usuários",
            baseDTO = clientDTO,
            onSuccess,
            onError
        )
    }
}