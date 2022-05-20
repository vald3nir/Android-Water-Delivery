package com.vald3nir.diskwater.domain.navigation

import android.content.Intent
import com.vald3nir.diskwater.common.core.AppView
import com.vald3nir.diskwater.common.extensions.hideKeyboard
import com.vald3nir.diskwater.common.utils.isAppClient
import com.vald3nir.diskwater.presentation.address.AddressActivity
import com.vald3nir.diskwater.presentation.dashboard.DashboardActivity
import com.vald3nir.diskwater.presentation.login.LoginActivity
import com.vald3nir.diskwater.presentation.orders.OrderActivity
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

    override fun redirectToLogin(appView: AppView?) {
        startActivity(appView, LoginActivity::class.java, newStack = true)
    }

    override fun redirectToRegister(appView: AppView?) {
        startActivity(appView, RegisterActivity::class.java)
    }

    override fun redirectToEditAddress(appView: AppView?) {
        startActivity(appView, AddressActivity::class.java)
    }

    override fun redirectToHome(appView: AppView?) {
        val activityClass = if (isAppClient()) {
            OrderActivity::class.java
        } else {
            DashboardActivity::class.java
        }
        startActivity(appView, activityClass, newStack = true)
    }
}