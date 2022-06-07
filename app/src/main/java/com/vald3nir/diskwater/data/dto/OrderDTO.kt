package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vald3nir.toolkit.data.dto.BaseDTO

@Entity
class OrderDTO(
    @PrimaryKey(autoGenerate = true)
    var key: Int = 1,
//    @TypeConverters(DateConverter.class)
//    var itens: List<OrderItemDTO>? = null,
    var clientName: String? = null,
    var address: String? = null,
    var date: String? = null,
    var total: Float = 0.0f
) : BaseDTO()