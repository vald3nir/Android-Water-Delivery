package com.vald3nir.diskwater.app.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.vald3nir.core_ui.CoreActivity

abstract class BaseActivity : CoreActivity() {

    fun setToolbarTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun registerForActivityWithResult(callback: (data: Intent?) -> Unit) =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                callback.invoke(data)
            }
        }
}