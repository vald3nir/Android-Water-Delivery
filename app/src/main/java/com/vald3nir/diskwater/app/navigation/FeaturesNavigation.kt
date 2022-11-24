package com.vald3nir.diskwater.app.navigation

import com.vald3nir.diskwater.app.base.BaseActivity

interface FeaturesNavigation {
    fun redirectToLogin(activity: BaseActivity?)
    fun redirectToProfile(activity: BaseActivity?)
    fun redirectToDashboard(activity: BaseActivity?)
    fun redirectToSales(activity: BaseActivity?)
}