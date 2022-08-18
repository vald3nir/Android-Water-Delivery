package com.vald3nir.dashboard.domain

import com.vald3nir.repository.dtos.ProductDTO
import com.vald3nir.core_ui.components.CustomListComponent

interface ProductUseCase {

    fun saveInMemory(item: com.vald3nir.repository.dtos.ProductDTO?)
    fun loadInMemory(): com.vald3nir.repository.dtos.ProductDTO?

    suspend fun updateProduct(
        product: com.vald3nir.repository.dtos.ProductDTO?,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun deleteProduct(
        product: com.vald3nir.repository.dtos.ProductDTO?,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun listProducts(
        category: String,
        onSuccess: (MutableList<com.vald3nir.repository.dtos.ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    fun listProductCategories(): List<String>

    fun listProductCategoriesTab(onCategorySelected: (category: String) -> Unit):
            ArrayList<CustomListComponent.CustomListTab>

}