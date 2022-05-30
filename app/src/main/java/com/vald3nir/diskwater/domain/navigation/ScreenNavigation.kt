package com.vald3nir.diskwater.domain.navigation

import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.toolkit.core.AppView
import com.vald3nir.toolkit.core.CoreFragment
import com.vald3nir.toolkit.data.BaseDTO

interface ScreenNavigation {
    fun createFragment(fragmentEnum: FragmentEnum): BaseFragment
    fun redirectToLogin(appView: AppView?)
    fun redirectToRegister(appView: AppView?)
    fun redirectToEditAddress(appView: AppView?)
    fun redirectToHome(appView: AppView?)
}