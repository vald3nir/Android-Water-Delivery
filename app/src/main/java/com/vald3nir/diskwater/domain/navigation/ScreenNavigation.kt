package com.vald3nir.diskwater.domain.navigation

import com.vald3nir.diskwater.common.core.AppView

interface ScreenNavigation {

    companion object {
        const val EDIT_ADDRESS_CODE = 1010
    }

    fun redirectToLogin(appView: AppView?)
    fun redirectToRegister(appView: AppView?)
    fun redirectToEditAddress(appView: AppView?)
    fun redirectToHome(appView: AppView?)
}