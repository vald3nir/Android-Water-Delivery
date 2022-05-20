package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import com.vald3nir.diskwater.common.core.BaseFragment

interface RequestCardFragmentFactory {

    fun createFragment(tab: Any): BaseFragment?

    fun createFragment(tab: Any, arguments: Bundle?): BaseFragment? {
        val fragment = createFragment(tab)
        fragment?.arguments = arguments
        return fragment
    }
}


