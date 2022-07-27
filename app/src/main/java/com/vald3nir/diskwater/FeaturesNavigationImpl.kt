package com.vald3nir.diskwater

import android.content.Intent
import com.vald3nir.commom.domain.navigation.FeaturesNavigation
import com.vald3nir.commom.presentation.view.BaseActivity
import com.vald3nir.core_ui.extensions.hideKeyboard
import com.vald3nir.dashboard.presentation.DashboardActivity
import com.vald3nir.login.presentation.LoginActivity
import com.vald3nir.sales.presentation.SalesActivity

class FeaturesNavigationImpl : FeaturesNavigation {

    private fun <T> BaseActivity.startNewActivity(
        activityClass: Class<T>
    ) {
        hideKeyboard()
        startActivity(createIntent(activityClass))
    }

    private fun <T> BaseActivity.startNewActivityForResult(
        activityClass: Class<T>,
        callback: (data: Intent?) -> Unit
    ) {
        hideKeyboard()
        registerForActivityWithResult(callback).launch(Intent(this, activityClass))
    }

    private fun <T> BaseActivity.createIntent(activityClass: Class<T>): Intent {
        val newIntent = Intent(this, activityClass)
      //  newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return newIntent
    }

    override fun redirectToLogin(activity: BaseActivity?) {
        activity?.run {
            startNewActivityForResult(LoginActivity::class.java) {
                if (isAppSalesman()) {
                    redirectToDashboard(activity)
                } else {
                    redirectToSales(activity)
                }
            }
        }
    }

    override fun redirectToProfile(activity: BaseActivity?) {
    }

    override fun redirectToDashboard(activity: BaseActivity?) {
        activity?.startNewActivity(DashboardActivity::class.java)
    }

    override fun redirectToSales(activity: BaseActivity?) {
        activity?.startNewActivity(SalesActivity::class.java)
    }
}