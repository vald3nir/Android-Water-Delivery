package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class OrderDTO(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 1,
//    @TypeConverters(DateConverter.class)
//    var itens: List<OrderItemDTO>? = null,
    var clientName: String? = null,
    var address: String? = null,
    var date: String? = null,
    var total: Float = 0.0f
) : BaseDTO()