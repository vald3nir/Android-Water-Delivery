package com.vald3nir.diskwater.domain.navigation

import com.vald3nir.diskwater.common.core.AppView

interface ScreenNavigation {
    fun redirectToLogin(appView: AppView?)
    fun redirectToRegister(appView: AppView?)
    fun redirectToHome(appView: AppView?)
}