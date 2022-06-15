package com.vald3nir.diskwater.common

import android.os.Bundle
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.toolkit.R
import com.vald3nir.toolkit.core.CoreFragmentActivity
import com.vald3nir.toolkit.data.dto.BaseDTO
import com.vald3nir.toolkit.databinding.ActivityEmptyBinding
import org.koin.android.ext.android.inject

class ActivityEmpty : CoreFragmentActivity() {

    companion object {
        const val FRAGMENT_ENUM_PARAM = "FRAGMENT_ENUM_PARAM"
    }

    private val screenNavigation: ScreenNavigation by inject()

    override fun registerViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(intent.getSerializableExtra(FRAGMENT_ENUM_PARAM) as FragmentEnum)
    }

    fun loadFragment(fragmentEnum: FragmentEnum, baseDTO: BaseDTO? = null) {
        val fragment = screenNavigation.createFragment(fragmentEnum)
        fragment.putExtraDTO(baseDTO)
        loadFragment(R.id.fragment_container, fragment)
    }
}