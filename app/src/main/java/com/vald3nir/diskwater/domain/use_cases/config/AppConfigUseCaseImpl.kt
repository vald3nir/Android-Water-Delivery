package com.vald3nir.diskwater.domain.use_cases.config

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vald3nir.diskwater.common.core.AppView
import com.vald3nir.diskwater.data.dto.AppConfigDTO
import com.vald3nir.diskwater.data.repository.remote.config.AppConfigRepository
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation

class AppConfigUseCaseImpl(
    private val repository: AppConfigRepository,
    private val screenNavigation: ScreenNavigation,
) : AppConfigUseCase {

    override fun loadConfiguration(
        appView: AppView?,
        onSuccess: (AppConfigDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    ) {
        val userId = Firebase.auth.currentUser?.uid
        if (userId != null) {
            repository.loadConfiguration(
                userId, onSuccess = {
                    onSuccess.invoke(it)
                }, onError = {
                    onError.invoke(it)
                }
            )
        } else {
            screenNavigation.redirectToLogin(appView)
        }
    }

    override fun updateConfiguration(
        appView: AppView?,
        appConfigDTO: AppConfigDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    ) {
        val userId = Firebase.auth.currentUser?.uid
        if (userId != null) {
            repository.updateConfiguration(userId, appConfigDTO, onSuccess, onError)
        } else {
            screenNavigation.redirectToLogin(appView)
        }
    }
}