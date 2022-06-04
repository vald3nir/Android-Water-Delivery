package com.vald3nir.diskwater.data.repository.remote.product

interface ProductRepository {

    suspend fun uploadProductImage(
        data: ByteArray,
        imagePath: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

}