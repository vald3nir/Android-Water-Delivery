package com.vald3nir.diskwater.domain.use_cases.register

import android.app.Activity
import com.vald3nir.diskwater.data.dto.ClientDTO

interface RegisterUseCase {

    suspend fun registerNewUser(
        activity: Activity?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun registerClient(
        activity: Activity?,
        clientDTO: ClientDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}