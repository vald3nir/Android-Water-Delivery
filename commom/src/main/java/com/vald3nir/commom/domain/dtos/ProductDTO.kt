package com.vald3nir.commom.domain.dtos

import com.vald3nir.repository.BaseDTO

data class ProductDTO(
    var isNew: Boolean = true,
    var category: String? = null,
    var name: String? = null,
    var price: Float? = 0.0f,
    var imageBase64: String? = null
) : BaseDTO()