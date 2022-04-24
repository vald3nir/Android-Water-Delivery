package com.vald3nir.diskwater.domain.use_cases.config

import com.vald3nir.diskwater.common.core.AppView
import com.vald3nir.diskwater.data.dto.AppConfigDTO


interface AppConfigUseCase {

    fun loadConfiguration(
        appView: AppView?,
        onSuccess: (AppConfigDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    fun updateConfiguration(
        appView: AppView?,
        appConfigDTO: AppConfigDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}