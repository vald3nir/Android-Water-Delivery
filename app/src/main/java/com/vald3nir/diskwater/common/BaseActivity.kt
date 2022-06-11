package com.vald3nir.diskwater.common

import com.vald3nir.diskwater.domain.utils.isAppClient
import com.vald3nir.toolkit.R
import com.vald3nir.toolkit.core.CoreActivity

abstract class BaseActivity : CoreActivity() {

    fun getTypeAppName(): String {
        return getString(
            if (isAppClient()) R.string.client else R.string.salesman
        )
    }
}