package com.vald3nir.diskwater.data.repository.local.daos

import androidx.room.Dao
import androidx.room.Query
import com.vald3nir.diskwater.data.dto.AddressDTO

@Dao
interface AddressDao : BaseDao<AddressDTO?> {

    @Query("SELECT * FROM addressDTO LIMIT 1")
    suspend fun first(): AddressDTO?
}