package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vald3nir.toolkit.data.BaseDTO

@Entity
class ProductDTO(
    @PrimaryKey(autoGenerate = true) var key: Int = 1,
    var name: String? = null,
    var price: Float? = null,
//    var imageURL: String? = null,
) : BaseDTO()