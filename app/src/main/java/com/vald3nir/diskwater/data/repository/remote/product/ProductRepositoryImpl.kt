package com.vald3nir.diskwater.data.repository.remote.product

import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.toolkit.data.repository.loadCollection
import com.vald3nir.toolkit.data.repository.updateData

class ProductRepositoryImpl : ProductRepository {

    override suspend fun updateProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        updateData(
            collectionPath = "produtos",
            baseDTO = product,
            onSuccess, onError
        )
    }

    override suspend fun listProducts(
        onSuccess: (MutableList<ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        loadCollection(
            collectionPath = "produtos",
            type = ProductDTO::class.java,
            onSuccess,
            onError
        )
    }
}