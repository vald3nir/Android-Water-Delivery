package com.vald3nir.diskwater.data.repository.local.daos

import androidx.room.*

@Dao
interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(type: T)

    @Update
    suspend fun update(type: T)

    @Delete
    suspend fun delete(type: T)

}