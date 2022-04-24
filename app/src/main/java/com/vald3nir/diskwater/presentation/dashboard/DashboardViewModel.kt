package com.vald3nir.diskwater.presentation.dashboard

import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.componets.CustomSheetDialog
import com.vald3nir.diskwater.common.core.BaseViewModel
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase

class DashboardViewModel(
    private val screenNavigation: ScreenNavigation,
    private val authUseCase: AuthUseCase,
) : BaseViewModel() {


    fun getMenuItems(): List<CustomSheetDialog.CustomItemSheet> {
        return listOf(
            CustomSheetDialog.CustomItemSheet(
                icon = R.drawable.ic_exit,
                title = getString(R.string.disconnect),
                action = { }
            ),
        )
    }


}