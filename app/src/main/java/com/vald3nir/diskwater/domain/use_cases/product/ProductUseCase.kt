package com.vald3nir.diskwater.domain.use_cases.product

import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.toolkit.core.componets.lists.CustomListComponent

interface ProductUseCase {

    suspend fun updateProduct(
        product: ProductDTO?,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun deleteProduct(
        product: ProductDTO?,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun listProducts(
        category: String,
        onSuccess: (MutableList<ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    fun listProductCategories(): List<String>

    fun listProductCategoriesTab(onCategorySelected: (category: String) -> Unit):
            ArrayList<CustomListComponent.CustomListTab>

}