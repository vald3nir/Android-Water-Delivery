package com.vald3nir.diskwater.domain.use_cases.product

import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.data.repository.product.ProductRepository
import com.vald3nir.toolkit.core.componets.lists.CustomListComponent

class ProductUseCaseImpl(
    private val repository: ProductRepository,
) : ProductUseCase {

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