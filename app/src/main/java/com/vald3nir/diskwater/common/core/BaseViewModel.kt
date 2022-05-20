package com.vald3nir.diskwater.common.core

import android.app.Activity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {

    var appView: AppView? = null

    fun getString(id: Int): String? {
        return appView?.getActivityContext()?.getString(id)
    }

    fun showMessage(message: String?) {
        appView?.showMessage(message)
    }

    fun showLoading(show: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                appView?.showLoading(show)
            }
        }
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