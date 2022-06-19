package com.vald3nir.diskwater.data.repository.product

import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.toolkit.data.repository.remote.firebase.FirebaseClient

class ProductRepositoryImpl : ProductRepository {

    private val firebaseClient = FirebaseClient()

    override suspend fun updateProduct(
        product: ProductDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        firebaseClient.updateData(
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