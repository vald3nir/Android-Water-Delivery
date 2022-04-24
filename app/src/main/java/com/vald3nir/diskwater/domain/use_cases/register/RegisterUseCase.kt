package com.vald3nir.diskwater.domain.use_cases.register

import com.vald3nir.diskwater.common.core.AppView

interface RegisterUseCase {

    fun registerNewUser(
        appView: AppView?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}