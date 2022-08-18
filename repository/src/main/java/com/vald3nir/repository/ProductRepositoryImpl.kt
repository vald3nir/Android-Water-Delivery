package com.vald3nir.repository

import com.vald3nir.core_repository.firebase.FirebaseClient

class ProductRepositoryImpl : ProductRepository {

    private val firebaseClient = FirebaseClient()

    override suspend fun updateProduct(
        product: com.vald3nir.repository.dtos.ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        firebaseClient.insertOrUpdateData(
            rootPath = "debug",
            document = "produtos",
            collection = "${product.category}",
            baseDTO = product,
            onSuccess,
            onError
        )
    }

    override suspend fun deleteProduct(
        product: com.vald3nir.repository.dtos.ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        firebaseClient.deleteData(
            rootPath = "debug",
            document = "produtos",
            collection = "${product.category}",
            baseDTO = product,
            onSuccess, onError
        )
    }

    override suspend fun listProducts(
        category: String,
        onSuccess: (MutableList<com.vald3nir.repository.dtos.ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        firebaseClient.loadCollection(
            rootPath = "debug",
            document = "produtos",
            collection = category,
            type = com.vald3nir.repository.dtos.ProductDTO::class.java,
            onSuccess,
            onError
        )
    }
}