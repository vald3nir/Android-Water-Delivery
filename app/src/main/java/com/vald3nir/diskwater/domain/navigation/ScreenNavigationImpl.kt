package com.vald3nir.diskwater.domain.navigation

import android.content.Intent
import com.vald3nir.diskwater.common.core.AppView
import com.vald3nir.diskwater.common.extensions.hideKeyboard
import com.vald3nir.diskwater.common.utils.isAppClient
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation.Companion.EDIT_ADDRESS_CODE
import com.vald3nir.diskwater.presentation.address.AddressActivity
import com.vald3nir.diskwater.presentation.dashboard.DashboardClientActivity
import com.vald3nir.diskwater.presentation.dashboard.DashboardSalesmanActivity
import com.vald3nir.diskwater.presentation.login.LoginActivity
import com.vald3nir.diskwater.presentation.register.RegisterActivity

class ScreenNavigationImpl : ScreenNavigation {


    private fun <T> startActivity(view: AppView?, classJava: Class<T>, newStack: Boolean = false) {
        view?.getActivityContext()?.apply {
            hideKeyboard()
            val newIntent = Intent(this, classJava)
            if (newStack) {
                newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(newIntent)
        }
    }

    private fun <T> startActivityForResult(view: AppView?, classJava: Class<T>, code: Int) {
        view?.getActivityContext()?.apply {
            hideKeyboard()
            val newIntent = Intent(this, classJava)
            startActivityForResult(newIntent, code)
        }
    }

    override fun redirectToLogin(appView: AppView?) {
        startActivity(appView, LoginActivity::class.java, newStack = true)
    }

    override fun redirectToRegister(appView: AppView?) {
        startActivity(appView, RegisterActivity::class.java)
    }

    override fun redirectToEditAddress(appView: AppView?) {
        startActivityForResult(appView, AddressActivity::class.java, code = EDIT_ADDRESS_CODE)
    }

    override fun redirectToHome(appView: AppView?) {
        val activityClass = if (isAppClient()) {
            DashboardClientActivity::class.java
        } else {
            DashboardSalesmanActivity::class.java
        }
        startActivity(appView, activityClass, newStack = true)
    }
}