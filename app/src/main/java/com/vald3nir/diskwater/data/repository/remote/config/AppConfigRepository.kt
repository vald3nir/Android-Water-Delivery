package com.vald3nir.diskwater.data.repository.remote.config

import com.vald3nir.diskwater.data.dto.AppConfigDTO

interface AppConfigRepository {

    fun loadConfiguration(
        userId: String,
        onSuccess: (AppConfigDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    fun updateConfiguration(
        userId: String,
        appConfigDTO: AppConfigDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}