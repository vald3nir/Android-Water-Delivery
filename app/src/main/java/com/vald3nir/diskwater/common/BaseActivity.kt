package com.vald3nir.diskwater.common

import com.vald3nir.diskwater.domain.isAppClient
import com.vald3nir.toolkit.R
import com.vald3nir.toolkit.core.CoreActivity

open class BaseActivity : CoreActivity() {

    fun getTypeAppName(): String {
        return getString(
            if (isAppClient()) R.string.client else R.string.salesman
        )
    }
}