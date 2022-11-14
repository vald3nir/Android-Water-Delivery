package com.vald3nir.core.presentation

interface FeaturesNavigation {
    fun redirectToLogin(activity: BaseActivity?)
    fun redirectToProfile(activity: BaseActivity?)
    fun redirectToDashboard(activity: BaseActivity?)
    fun redirectToSales(activity: BaseActivity?)
}