package com.vald3nir.diskwater.domain.navigation

import com.vald3nir.toolkit.core.AppView
import com.vald3nir.toolkit.core.CoreFragment

interface ScreenNavigation {
    fun createFragment(fragmentEnum: FragmentEnum): CoreFragment
    fun redirectToLogin(appView: AppView?)
    fun redirectToRegister(appView: AppView?)
    fun redirectToEditAddress(appView: AppView?)
    fun redirectToHome(appView: AppView?)
}