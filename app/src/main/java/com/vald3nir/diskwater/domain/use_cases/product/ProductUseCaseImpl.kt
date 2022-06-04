package com.vald3nir.diskwater.domain.use_cases.product

import android.graphics.Bitmap
import com.vald3nir.diskwater.data.repository.remote.product.ProductRepository
import java.io.ByteArrayOutputStream

class ProductUseCaseImpl(
    private val repository: ProductRepository,
) : ProductUseCase {

    override suspend fun uploadProductImage(
        bitmap: Bitmap,
        productName: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val imagePath = "images/$productName.jpg"
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val data = stream.toByteArray()
        repository.uploadProductImage(data, imagePath, onSuccess, onError)
    }
}