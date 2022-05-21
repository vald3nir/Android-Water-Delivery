package com.vald3nir.diskwater.data.repository.local.daos

import androidx.room.Dao
import androidx.room.Query
import com.vald3nir.diskwater.data.dto.ProductDTO

@Dao
interface ProductDao : BaseDao<ProductDTO?> {

    @Query("SELECT * FROM productDTO LIMIT 1")
    suspend fun first(): ProductDTO?

    @Query("DELETE FROM productDTO")
    suspend fun deleteAll()
}