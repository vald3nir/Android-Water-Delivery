package com.vald3nir.diskwater.data.repository.auth

import android.app.Activity
import android.content.Context
import com.vald3nir.diskwater.data.dto.LoginDTO
import com.vald3nir.toolkit.data.repository.local.loadDataJson
import com.vald3nir.toolkit.data.repository.local.saveDataJson
import com.vald3nir.toolkit.data.repository.remote.firebase.FirebaseAuthenticator
import com.vald3nir.toolkit.extensions.toDTO

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

    override suspend fun saveLoginData(context: Context?, loginDTO: LoginDTO) {
        if (loginDTO.rememberLogin) {
            context?.saveDataJson("Login", loginDTO.toJson())
        } else {
            context?.saveDataJson("Login", null)
        }
    }

    override suspend fun loadLoginData(context: Context?): LoginDTO {
        val json = context?.loadDataJson("Login")
        return json.toDTO(LoginDTO::class.java)
    }
}