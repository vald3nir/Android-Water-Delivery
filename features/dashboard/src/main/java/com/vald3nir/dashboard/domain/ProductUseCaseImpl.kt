package com.vald3nir.dashboard.domain

import com.vald3nir.commom.domain.dtos.ProductDTO
import com.vald3nir.core_ui.components.CustomListComponent
import com.vald3nir.dashboard.repository.ProductRepository

class ProductUseCaseImpl(
    private val repository: ProductRepository,
) : ProductUseCase {

    private var product: ProductDTO? = null

    override fun saveInMemory(item: ProductDTO?) {
        product = item
    }

    override fun loadInMemory(): ProductDTO? {
        return product
    }

    override suspend fun updateProduct(
        product: ProductDTO?,
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
        product: ProductDTO?,
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
        onSuccess: (MutableList<ProductDTO>) -> Unit,
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