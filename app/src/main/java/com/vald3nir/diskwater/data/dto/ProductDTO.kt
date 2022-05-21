package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductDTO(
    @PrimaryKey(autoGenerate = true) var uid: Int = 1,
    var name: String? = null,
    var price: Float? = null,
) : BaseDTO()