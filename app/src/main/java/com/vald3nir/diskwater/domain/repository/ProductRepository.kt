package com.vald3nir.diskwater.domain.repository

import com.vald3nir.diskwater.domain.dtos.ProductDTO

interface ProductRepository {

    suspend fun updateProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun deleteProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun listProducts(
        category: String,
        onSuccess: (MutableList<ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}