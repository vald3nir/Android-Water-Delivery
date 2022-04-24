package com.vald3nir.diskwater.data.repository.remote.register

import android.app.Activity

interface RegisterRepository {

    fun registerNewUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}