package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.core.BaseActivity
import com.vald3nir.diskwater.common.core.BaseFragment
import com.vald3nir.diskwater.common.extensions.hideKeyboard
import com.vald3nir.diskwater.databinding.ActivityOrderBinding
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import org.koin.ext.getFullName


class OrderActivity : BaseActivity() {

    private val baseFragmentFactory: RequestCardFragmentFactory by inject(
        named(
            RequestCardFactoryBase.NAME
        )
    )


    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun loadFragment(fragmentsEnum: FragmentsEnum) {
        hideKeyboard()

        val fragment: BaseFragment = baseFragmentFactory.createFragment(fragmentsEnum) ?: return
        fragment.let { it.appView = this }

        val fragmentName = fragment.let { it::class.getFullName() }
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(fragmentName)
        fragmentTransaction.commitAllowingStateLoss()
    }


}