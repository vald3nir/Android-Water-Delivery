package com.vald3nir.diskwater.data.repository.remote.product

import com.vald3nir.diskwater.data.dto.ProductDTO

interface ProductRepository {

    suspend fun updateProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun listProducts(
        onSuccess: (MutableList<ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}