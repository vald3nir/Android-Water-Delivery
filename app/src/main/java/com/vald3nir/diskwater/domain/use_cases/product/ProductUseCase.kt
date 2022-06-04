package com.vald3nir.diskwater.domain.use_cases.product

import android.graphics.Bitmap

interface ProductUseCase {

    suspend fun uploadProductImage(
        bitmap: Bitmap,
        productName: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

}