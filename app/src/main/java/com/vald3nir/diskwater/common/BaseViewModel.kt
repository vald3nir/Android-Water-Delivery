package com.vald3nir.diskwater.common

import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.toolkit.core.CoreViewModel
import com.vald3nir.toolkit.data.dto.BaseDTO

open class BaseViewModel : CoreViewModel() {

    fun replaceFragment(fragmentEnum: FragmentEnum, baseDTO: BaseDTO? = null) {
        controller?.requireActivityContext().let {
            if (it is ActivityEmpty) {
                it.loadFragment(fragmentEnum, baseDTO)
            }
        }
    }
}