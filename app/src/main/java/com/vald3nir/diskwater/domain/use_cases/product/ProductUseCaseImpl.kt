package com.vald3nir.diskwater.domain.use_cases.product

import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.data.repository.remote.product.ProductRepository

class ProductUseCaseImpl(
    private val repository: ProductRepository,
) : ProductUseCase {

    override suspend fun insertNewProduct(
        product: ProductDTO?,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        if (product == null) {
            onError.invoke(Exception("Produto não preenchido"))
        } else {
            product.let { repository.insertNewProduct(it, onSuccess, onError) }
        }
    }

    override suspend fun updateProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
    }
}