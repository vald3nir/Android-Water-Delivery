package com.vald3nir.diskwater.data.repository.remote.register

import android.app.Activity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterRepositoryImpl : RegisterRepository {

    override fun registerNewUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                if (it.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke(it.exception)
                }
            }
    }
}