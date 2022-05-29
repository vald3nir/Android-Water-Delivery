package com.vald3nir.toolkit.core

import android.app.Activity
import androidx.lifecycle.ViewModel

open class CoreViewModel : ViewModel() {

    var appView: AppView? = null

    fun getString(id: Int): String? {
        return appView?.getActivityContext()?.getString(id)
    }

    fun showMessage(message: String?) {
        appView?.showMessage(message)
    }

    fun showLoading(show: Boolean) {
        appView?.showLoading(show)
    }

    fun showError(it: Exception?) {
        appView?.showLoading(false)
        appView?.showMessage(it?.message)
    }

    fun finish() {
        appView?.getActivityContext()?.apply {
            showLoading(false)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}