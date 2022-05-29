package com.vald3nir.toolkit.core

import android.app.Activity

interface AppView {
    fun getActivityContext(): Activity?
    fun onBackPressed()
    fun showMessage(message: String?)
    fun showMessage(message: Int)
    fun showLoading(show: Boolean)
}