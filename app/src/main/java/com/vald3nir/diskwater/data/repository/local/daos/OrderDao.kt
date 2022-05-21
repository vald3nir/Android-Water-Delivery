package com.vald3nir.diskwater.data.repository.local.daos

import androidx.room.Dao
import androidx.room.Query
import com.vald3nir.diskwater.data.dto.OrderDTO

@Dao
interface OrderDao : BaseDao<OrderDTO?> {

    @Query("SELECT * FROM orderDTO LIMIT 1")
    suspend fun first(): OrderDTO?

    @Query("DELETE FROM orderDTO")
    suspend fun deleteAll()
}