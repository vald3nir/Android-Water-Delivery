package com.vald3nir.diskwater.domain.navigation

import android.app.Activity
import android.content.Intent
import com.vald3nir.diskwater.common.ActivityEmpty
import com.vald3nir.diskwater.common.ActivityEmpty.Companion.FRAGMENT_ENUM_PARAM
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.domain.utils.isAppClient
import com.vald3nir.diskwater.presentation.dashboard.DashboardFragment
import com.vald3nir.diskwater.presentation.login.LoginActivity
import com.vald3nir.diskwater.presentation.orders.*
import com.vald3nir.diskwater.presentation.product.ProductDetailFragment
import com.vald3nir.diskwater.presentation.product.ProductsFragment
import com.vald3nir.diskwater.presentation.register.RegisterActivity
import com.vald3nir.toolkit.utils.extensions.hideKeyboard

class ScreenNavigationImpl : ScreenNavigation {

    private fun startActivity(activity: Activity, intent: Intent, newStack: Boolean = false) {
        activity.apply {
            hideKeyboard()
            if (newStack) intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun <T> startActivity(
        activity: Activity?,
        activityClass: Class<T>,
        newStack: Boolean = false
    ) {
        activity?.apply {
            val newIntent = Intent(this, activityClass)
            startActivity(this, newIntent, newStack)
        }
    }

    override fun createFragment(fragmentEnum: FragmentEnum): BaseFragment {
        return when (fragmentEnum) {
            FragmentEnum.MY_ORDERS -> MyOrdersFragment()
            FragmentEnum.CONFIRM_ADDRESS -> ConfirmAddressFragment()
            FragmentEnum.SHOPPING_CART -> ShoppingCartFragment()
            FragmentEnum.ORDER_DETAIL -> OrderDetailFragment()
            FragmentEnum.PAYMENT -> PaymentFragment()
            FragmentEnum.ORDER_SUCCESS -> OrderSuccessFragment()
            FragmentEnum.DASHBOARD -> DashboardFragment()
            FragmentEnum.PRODUCTS -> ProductsFragment()
            FragmentEnum.PRODUCT_DETAIL -> ProductDetailFragment()
        }
    }

    override fun redirectToHome(activity: Activity?) {
        val fragmentEnum = if (isAppClient()) {
            FragmentEnum.MY_ORDERS
        } else {
            FragmentEnum.DASHBOARD
        }
        activity?.apply {
            val intent = Intent(this, ActivityEmpty::class.java)
            intent.putExtra(FRAGMENT_ENUM_PARAM, fragmentEnum)
            startActivity(this, intent, true)
        }
    }

    override fun redirectToLogin(activity: Activity?) {
        startActivity(activity, LoginActivity::class.java, newStack = true)
    }

    override fun redirectToRegister(activity: Activity?) {
        startActivity(activity, RegisterActivity::class.java)
    }

    override fun redirectToEditAddress(activity: Activity?) {
    }
}