package com.vald3nir.diskwater.presentation.product

import com.vald3nir.toolkit.core.CoreViewModel
import com.vald3nir.diskwater.data.dto.ProductDTO

class ProductViewModel(
) : CoreViewModel() {

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
}