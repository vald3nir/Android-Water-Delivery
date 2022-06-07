package com.vald3nir.diskwater.domain.use_cases.product

import com.vald3nir.diskwater.data.dto.ProductDTO

interface ProductUseCase {

    suspend fun insertNewProduct(
        product: ProductDTO?,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun updateProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}