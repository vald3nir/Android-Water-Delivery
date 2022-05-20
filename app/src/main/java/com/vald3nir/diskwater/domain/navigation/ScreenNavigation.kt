package com.vald3nir.diskwater.domain.navigation

import com.vald3nir.diskwater.common.core.AppView

interface ScreenNavigation {

    companion object {
        const val LOGIN_CODE = 1010
        const val EDIT_ADDRESS_CODE = 1011
        const val EDIT_ORDER_CODE = 1012
    }

    fun redirectToLogin(appView: AppView?)
    fun redirectToRegister(appView: AppView?)
    fun redirectToEditAddress(appView: AppView?)
    fun redirectToHome(appView: AppView?)
}