package com.vald3nir.diskwater.domain.use_cases.register

import android.app.Activity

interface RegisterUseCase {

    suspend fun registerNewUser(
        activity: Activity?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun registerUserType(
        activity: Activity?,
        userID: String,
        isSalesman: Boolean,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}