package com.vald3nir.diskwater.common

import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.toolkit.core.CoreViewModel
import com.vald3nir.toolkit.data.BaseDTO

open class BaseViewModel : CoreViewModel() {

    fun replaceFragment(fragmentEnum: FragmentEnum, baseDTO: BaseDTO? = null) {
        appView?.getActivityContext().let {
            if (it is ActivityEmpty) {
                it.loadFragment(fragmentEnum, baseDTO)
            }
        }
    }
}