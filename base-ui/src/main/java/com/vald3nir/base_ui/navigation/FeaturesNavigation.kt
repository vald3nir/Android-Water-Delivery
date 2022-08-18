package com.vald3nir.base_ui.navigation

import com.vald3nir.base_ui.view.BaseActivity

interface FeaturesNavigation {
    fun redirectToLogin(activity: BaseActivity?)
    fun redirectToProfile(activity: BaseActivity?)
    fun redirectToDashboard(activity: BaseActivity?)
    fun redirectToSales(activity: BaseActivity?)
}