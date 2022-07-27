package com.vald3nir.diskwater

import android.os.Bundle
import com.vald3nir.commom.domain.navigation.FeaturesNavigation
import com.vald3nir.commom.presentation.view.BaseActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val screenNavigation: FeaturesNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenNavigation.redirectToLogin(this)
    }

    override fun registerViewModel() {}
}