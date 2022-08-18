package com.vald3nir.dashboard.domain

import com.vald3nir.core_ui.components.CustomListComponent
import com.vald3nir.repository.ProductRepository

class ProductUseCaseImpl(
    private val repository: ProductRepository,
) : ProductUseCase {

    private var product: com.vald3nir.repository.dtos.ProductDTO? = null

    override fun saveInMemory(item: com.vald3nir.repository.dtos.ProductDTO?) {
        product = item
    }

    override fun loadInMemory(): com.vald3nir.repository.dtos.ProductDTO? {
        return product
    }

    override suspend fun updateProduct(
        product: com.vald3nir.repository.dtos.ProductDTO?,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        if (product == null) {
            onError.invoke(Exception("Produto não preenchido"))
        } else {
            product.let { repository.updateProduct(it, onSuccess, onError) }
        }
    }

    override suspend fun deleteProduct(
        product: com.vald3nir.repository.dtos.ProductDTO?,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        if (product == null) {
            onError.invoke(Exception("Produto não preenchido"))
        } else {
            repository.deleteProduct(product, onSuccess, onError)
        }
    }

    override suspend fun listProducts(
        category: String,
        onSuccess: (MutableList<com.vald3nir.repository.dtos.ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        repository.listProducts(category, onSuccess, onError)
    }

    override fun listProductCategories() = listOf("Água Mineral", "Descartáveis")

    override fun listProductCategoriesTab(onCategorySelected: (category: String) -> Unit): ArrayList<CustomListComponent.CustomListTab> {
        val tabs = ArrayList<CustomListComponent.CustomListTab>()
        listProductCategories().map {
            tabs.add(
                CustomListComponent.CustomListTab(
                    title = it,
                    onTabSelectedListener = { onCategorySelected.invoke(it) }
                ),
            )
        }
        return tabs
    }

}