package com.vald3nir.diskwater.domain.repository

import com.vald3nir.core_repository.firebase.FirebaseClient
import com.vald3nir.diskwater.domain.dtos.ProductDTO

class ProductRepositoryImpl : ProductRepository {

    private val firebaseClient = FirebaseClient()

    override suspend fun updateProduct(
        product: ProductDTO,
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
        product: ProductDTO,
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
        onSuccess: (MutableList<ProductDTO>) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        firebaseClient.loadCollection(
            rootPath = "debug",
            document = "produtos",
            collection = category,
            type = ProductDTO::class.java,
            onSuccess,
            onError
        )
    }
}