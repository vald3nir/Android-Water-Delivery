package com.vald3nir.diskwater.common.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.concurrent.schedule

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

    fun runDelay(delay: Long = 2000, callback: () -> Unit) {
        Timer().schedule(delay) {
            callback.invoke()
        }
    }

    fun runOnBackground(callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch { callback.invoke() }
    }

    fun runOnMainThread(callback: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch { callback.invoke() }
    }
}