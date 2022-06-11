package com.vald3nir.diskwater.domain.use_cases.product

import com.vald3nir.diskwater.data.dto.ProductDTO

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
        onSuccess: (MutableList<ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}