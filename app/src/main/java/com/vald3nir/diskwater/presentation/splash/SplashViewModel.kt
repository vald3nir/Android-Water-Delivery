package com.vald3nir.diskwater.presentation.splash

import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.common.core.BaseViewModel
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import com.vald3nir.diskwater.domain.use_cases.sensor.SensorUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val screenNavigation: ScreenNavigation,
    private val sensorUseCase: SensorUseCase,
    private val authUseCase: AuthUseCase,
) : BaseViewModel() {

    fun loadAppConfig() {
        runDelay(callback = {
            showLoading(show = true)
            viewModelScope.launch {
                val userID = authUseCase.getUserID()
                if (userID == null) {
                    screenNavigation.redirectToLogin(view)
                } else {
                    sensorUseCase.getSensorList(
                        userID = userID,
                        onSuccess = {
                            showLoading(false)
                            if (it?.items.isNullOrEmpty()) {
                                screenNavigation.redirectToSensorManager(view)
                            } else {
                                screenNavigation.redirectToHome(view)
                            }
                        },
                        onError = {
                            showLoading(false)
                            showMessage(it?.message)
                        }
                    )
                }
            }
        })
    }
}
