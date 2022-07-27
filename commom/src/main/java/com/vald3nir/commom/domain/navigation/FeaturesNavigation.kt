package com.vald3nir.commom.domain.navigation

import com.vald3nir.commom.presentation.view.BaseActivity

interface FeaturesNavigation {
    fun redirectToLogin(activity: BaseActivity?)
    fun redirectToProfile(activity: BaseActivity?)
    fun redirectToDashboard(activity: BaseActivity?)
    fun redirectToSales(activity: BaseActivity?)
}