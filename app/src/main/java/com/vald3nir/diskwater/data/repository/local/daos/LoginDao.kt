package com.vald3nir.diskwater.data.repository.local.daos

import androidx.room.Dao
import androidx.room.Query
import com.vald3nir.diskwater.data.dto.LoginDTO

@Dao
interface LoginDao : BaseDao<LoginDTO?> {

    @Query("SELECT * FROM loginDTO LIMIT 1")
    suspend fun first(): LoginDTO?

    @Query("DELETE FROM loginDTO")
    suspend fun deleteAll()
}