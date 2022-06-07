package com.vald3nir.diskwater.data.repository.remote.product

import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.toolkit.data.repository.updateData

class ProductRepositoryImpl : ProductRepository {

    override suspend fun insertNewProduct(
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
}