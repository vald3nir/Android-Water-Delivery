package com.vald3nir.diskwater.data.repository.remote.product

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProductRepositoryImpl : ProductRepository {

    override suspend fun uploadProductImage(
        data: ByteArray,
        imagePath: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference
        val mountainsRef = storageRef.child(imagePath)
        val uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener { onError.invoke(it) }
            .addOnSuccessListener { onSuccess.invoke() }
    }
}