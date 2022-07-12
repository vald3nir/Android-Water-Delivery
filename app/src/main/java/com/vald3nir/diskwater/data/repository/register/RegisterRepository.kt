package com.vald3nir.diskwater.data.repository.register

import android.app.Activity
import com.vald3nir.diskwater.data.dto.ClientDTO

interface RegisterRepository {

    suspend fun registerNewUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun registerClient(
        activity: Activity,
        clientDTO: ClientDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}