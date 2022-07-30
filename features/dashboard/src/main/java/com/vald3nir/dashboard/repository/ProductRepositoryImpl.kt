package com.vald3nir.dashboard.repository

import com.vald3nir.commom.domain.dtos.ProductDTO
import com.vald3nir.repository.firebase.FirebaseClient

class ProductRepositoryImpl : ProductRepository {

    private val firebaseClient = FirebaseClient()

    override suspend fun updateProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        firebaseClient.insertOrUpdateData(
            rootPath = "produtos",
            document = "categoria",
            collection = "${product.category}",
            baseDTO = product,
            onSuccess,
            onError
        )
    }

    override suspend fun deleteProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        firebaseClient.deleteData(
            rootPath = "produtos",
            document = "categoria",
            collection = "${product.category}",
            baseDTO = product,
            onSuccess, onError
        )
    }

    override suspend fun listProducts(
        category: String,
        onSuccess: (MutableList<ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        firebaseClient.loadCollection(
            rootPath = "produtos",
            document = "categoria",
            collection = category,
            type = ProductDTO::class.java,
            onSuccess,
            onError
        )
    }
}