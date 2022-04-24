package com.vald3nir.diskwater.common.core

import android.app.Activity
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(), AppView {

    override fun getActivityContext(): Activity? {
        return activity
    }

    override fun showMessage(message: String?) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showMessage(message)
        }
    }

    override fun showMessage(message: Int) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showMessage(message)
        }
    }

    override fun showLoading(show: Boolean) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showLoading(show)
        }
    }
}