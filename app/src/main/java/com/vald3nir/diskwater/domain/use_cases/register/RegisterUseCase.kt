package com.vald3nir.diskwater.domain.use_cases.register

import com.vald3nir.toolkit.core.AppView

interface RegisterUseCase {

    suspend fun registerNewUser(
        appView: AppView?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun registerUserType(
        appView: AppView?,
        userID: String,
        isSalesman: Boolean,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}