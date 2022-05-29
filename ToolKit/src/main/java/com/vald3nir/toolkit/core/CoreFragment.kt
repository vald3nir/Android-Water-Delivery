package com.vald3nir.toolkit.core

import androidx.fragment.app.Fragment

open class CoreFragment : Fragment() {

    var appView: AppView? = null

    fun showMessage(message: String?) {
        appView?.showMessage(message)
    }

    fun showMessage(message: Int) {
        appView?.showMessage(message)
    }

    fun showLoading(show: Boolean) {
        appView?.showLoading(show)
    }
}