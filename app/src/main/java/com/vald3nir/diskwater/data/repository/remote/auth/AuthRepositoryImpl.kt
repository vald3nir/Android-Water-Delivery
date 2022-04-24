package com.vald3nir.diskwater.data.repository.remote.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vald3nir.diskwater.data.dto.LoginDTO
import com.vald3nir.diskwater.data.repository.local.LocalPreferencesRepository

class AuthRepositoryImpl(
    private val localPreferences: LocalPreferencesRepository
) : AuthRepository {

    override suspend fun disconnect() {
        Firebase.auth.signOut()
    }

    override suspend fun loadCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override suspend fun login(
        activity: Activity,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {

        val email: String = loginDTO.email ?: ""
        val password: String = loginDTO.password ?: ""

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                if (it.isSuccessful) {
                    localPreferences.saveLoginPreferences(if (loginDTO.rememberLogin) loginDTO else null)
                    onSuccess.invoke()
                } else {
                    onError.invoke(it.exception)
                }
            }
    }

    override suspend fun loadLoginData(
        onSuccess: (loginDTO: LoginDTO?) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        onSuccess.invoke(localPreferences.loadLoginPreferences())
    }
}