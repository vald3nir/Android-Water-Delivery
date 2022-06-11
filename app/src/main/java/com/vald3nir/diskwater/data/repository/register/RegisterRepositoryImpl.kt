package com.vald3nir.diskwater.data.repository.register

import android.app.Activity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterRepositoryImpl : RegisterRepository {

    override suspend fun registerNewUser(
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

    override suspend fun registerUserType(
        activity: Activity,
        userID: String,
        isSalesman: Boolean,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val path = if (isSalesman) "vendedores" else "clientes"
        val data = HashMap<String, String>()
        data["id"] = userID

        Firebase.firestore.collection(path).document(path).collection(path).add(data)
            .addOnCompleteListener(activity) {
                if (it.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke(it.exception)
                }
            }
    }
}