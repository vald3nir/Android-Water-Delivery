package com.vald3nir.diskwater.app

import android.os.Bundle
import com.vald3nir.diskwater.app.base.BaseActivity
import com.vald3nir.diskwater.app.navigation.FeaturesNavigation
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val screenNavigation: FeaturesNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenNavigation.redirectToLogin(this)
    }

    override fun registerViewModel() {}
}