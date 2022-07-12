package com.vald3nir.diskwater.data.repository.register

import android.app.Activity
import com.vald3nir.diskwater.data.dto.ClientDTO
import com.vald3nir.toolkit.data.repository.remote.firebase.FirebaseAuthenticator
import com.vald3nir.toolkit.data.repository.remote.firebase.FirebaseClient

class RegisterRepositoryImpl : RegisterRepository {

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
            rootPath = "usuários",
            document = "clientes",
            collection = "clientes",
            baseDTO = clientDTO,
            onSuccess,
            onError
        )
//        Firebase.firestore.collection(path).document(path).collection(path).add(data)
//            .addOnCompleteListener(activity) {
//                if (it.isSuccessful) {
//                    onSuccess.invoke()
//                } else {
//                    onError.invoke(it.exception)
//                }
//            }
    }
}