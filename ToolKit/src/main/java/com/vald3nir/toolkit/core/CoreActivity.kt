package com.vald3nir.toolkit.core

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vald3nir.toolkit.componets.CustomLoadingScreen
import com.vald3nir.toolkit.extensions.hideKeyboard

open class CoreActivity : AppCompatActivity(), AppView {

    private var toast: Toast? = null
    private var customLoadingScreen: CustomLoadingScreen? = null

    override fun onResume() {
        super.onResume()
        showLoading(false)
        hideKeyboard()
    }

    override fun getActivityContext(): Activity? {
        return this
    }

    override fun showMessage(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun showMessage(message: Int) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            customLoadingScreen?.dismiss()
            customLoadingScreen = CustomLoadingScreen(this)
            customLoadingScreen?.show()
        } else {
            customLoadingScreen?.dismiss()
            customLoadingScreen = null
        }
    }
}