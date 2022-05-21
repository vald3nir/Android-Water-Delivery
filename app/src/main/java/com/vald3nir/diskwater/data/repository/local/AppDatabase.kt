package com.vald3nir.diskwater.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vald3nir.diskwater.data.dto.AddressDTO
import com.vald3nir.diskwater.data.dto.LoginDTO
import com.vald3nir.diskwater.data.dto.OrderDTO
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.data.repository.local.daos.AddressDao
import com.vald3nir.diskwater.data.repository.local.daos.LoginDao
import com.vald3nir.diskwater.data.repository.local.daos.OrderDao
import com.vald3nir.diskwater.data.repository.local.daos.ProductDao

@Database(
    version = 1, exportSchema = false,
    entities = [
        LoginDTO::class,
//        ClientDTO::class,
        AddressDTO::class,
        ProductDTO::class,
        OrderDTO::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLoginDao(): LoginDao
    abstract fun getAddressDao(): AddressDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getProductDao(): ProductDao
}