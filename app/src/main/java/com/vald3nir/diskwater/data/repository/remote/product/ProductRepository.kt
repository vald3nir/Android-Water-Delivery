package com.vald3nir.diskwater.data.repository.remote.product

import com.vald3nir.diskwater.data.dto.ProductDTO

interface ProductRepository {

    suspend fun insertNewProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}