package com.vald3nir.diskwater.common.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    var view: AppView? = null

    fun getString(id: Int): String? {
        return view?.getActivityContext()?.getString(id)
    }

    fun showMessage(message: String?) {
        view?.showMessage(message)
    }

    fun showLoading(show: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                view?.showLoading(show)
            }
        }
    }

    fun showError(it: Exception?) {
        view?.showLoading(false)
        view?.showMessage(it?.message)
    }

    fun finish() {
        view?.getActivityContext()?.finish()
    }
}