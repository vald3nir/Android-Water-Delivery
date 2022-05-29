package com.vald3nir.diskwater.common

import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.toolkit.core.CoreViewModel

open class BaseViewModel : CoreViewModel() {

    fun replaceFragment(fragmentEnum: FragmentEnum) {
        appView?.getActivityContext().let {
            if (it is ActivityEmpty) {
                it.loadFragment(fragmentEnum)
            }
        }
    }
}