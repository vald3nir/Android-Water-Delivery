package com.vald3nir.diskwater.data.dto

import com.vald3nir.toolkit.data.dto.BaseDTO

class ProductDTO(
    var isNew: Boolean = true,
    var category: String? = null,
    var name: String? = null,
    var price: Float? = 0.0f,
    var imageBase64: String? = null
) : BaseDTO()