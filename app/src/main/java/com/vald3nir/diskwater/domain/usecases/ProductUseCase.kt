package com.vald3nir.diskwater.domain.usecases

import com.vald3nir.core_ui.components.CustomListComponent
import com.vald3nir.diskwater.domain.dtos.ProductDTO

interface ProductUseCase {

    fun saveInMemory(item: ProductDTO?)
    fun loadInMemory(): ProductDTO?

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