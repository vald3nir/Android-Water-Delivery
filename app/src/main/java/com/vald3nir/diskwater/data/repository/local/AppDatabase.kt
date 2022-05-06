package com.vald3nir.diskwater.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vald3nir.diskwater.data.dto.AddressDTO
import com.vald3nir.diskwater.data.dto.LoginDTO
import com.vald3nir.diskwater.data.repository.local.daos.AddressDao
import com.vald3nir.diskwater.data.repository.local.daos.LoginDao

@Database(
    entities = [LoginDTO::class, AddressDTO::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLoginDao(): LoginDao
    abstract fun getAddressDao(): AddressDao
}