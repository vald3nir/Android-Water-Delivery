package com.vald3nir.diskwater.data.repository.remote.register

import android.app.Activity

interface RegisterRepository {

    suspend fun registerNewUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun registerUserType(
        activity: Activity,
        userID: String,
        isSalesman: Boolean,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}