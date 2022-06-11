package com.vald3nir.diskwater.domain.navigation

import android.app.Activity
import com.vald3nir.diskwater.common.BaseFragment

interface ScreenNavigation {
    fun createFragment(fragmentEnum: FragmentEnum): BaseFragment
    fun redirectToLogin(activity: Activity?)
    fun redirectToRegister(activity: Activity?)
    fun redirectToEditAddress(activity: Activity?)
    fun redirectToHome(activity: Activity?)
}