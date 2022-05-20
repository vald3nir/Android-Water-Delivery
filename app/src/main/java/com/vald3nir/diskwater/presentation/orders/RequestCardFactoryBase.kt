package com.vald3nir.diskwater.presentation.orders

import com.vald3nir.diskwater.common.core.BaseFragment
import com.vald3nir.diskwater.presentation.orders.fragments.MyOrdersFragment
import com.vald3nir.diskwater.presentation.orders.fragments.PaymentMethodsFragment

class RequestCardFactoryBase : RequestCardFragmentFactory {

    companion object {
        const val NAME = "My Fr"
    }

    override fun createFragment(tab: Any): BaseFragment? {
        return when (tab) {
            FragmentsEnum.MY_ORDERS -> MyOrdersFragment()
            FragmentsEnum.PAYMENT -> PaymentMethodsFragment()
            else -> null
        }
    }
}

