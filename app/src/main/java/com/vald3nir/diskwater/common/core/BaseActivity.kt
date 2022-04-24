package com.vald3nir.diskwater.common.core

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vald3nir.diskwater.common.componets.LoadingScreenDialog

open class BaseActivity : AppCompatActivity(), AppView {

    private var toast: Toast? = null
    private var loadingScreenDialog: LoadingScreenDialog? = null

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
            loadingScreenDialog?.dismiss()
            loadingScreenDialog = LoadingScreenDialog(this)
            loadingScreenDialog?.show()
        } else {
            loadingScreenDialog?.dismiss()
        }
    }




//    fun hasAndroidPermission(permission: String): Boolean {
//        return EasyPermissions.hasPermissions(this, permission)
//    }
//
//    fun requestPermissions(permission: String, requestCode: Int, message: String) {
//        EasyPermissions.requestPermissions(this, message, requestCode, permission)
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
//    }
}