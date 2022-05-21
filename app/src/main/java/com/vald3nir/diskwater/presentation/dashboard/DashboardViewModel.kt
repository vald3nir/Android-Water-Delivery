package com.vald3nir.diskwater.presentation.dashboard

import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.componets.CustomSheetDialog
import com.vald3nir.diskwater.common.core.BaseViewModel
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase

class DashboardViewModel(
    private val screenNavigation: ScreenNavigation,
    private val authUseCase: AuthUseCase,
) : BaseViewModel() {


    val products = listOf<ProductDTO>(
        ProductDTO(
            name = "Renágua 20L",
            price = 5.20f
        ),
        ProductDTO(
            name = "Clareza 20L",
            price = 5.80f
        ),
        ProductDTO(
            name = "Indaiá 20L",
            price = 11.50f
        ),
        ProductDTO(
            name = "Naturágua 20L",
            price = 11.90f
        ),
        ProductDTO(
            name = "Neblina 20L",
            price = 11.90f
        )
    )


    fun getMenuItems(): List<CustomSheetDialog.CustomItemSheet> {
        return listOf(
            CustomSheetDialog.CustomItemSheet(
                icon = R.drawable.ic_update,
                title = "Atualizar produtos",
                action = { replaceFragment(FragmentEnum.PRODUCTS) }
            ),
        )
    }


}