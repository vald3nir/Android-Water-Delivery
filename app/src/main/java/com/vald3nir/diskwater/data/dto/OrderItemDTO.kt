package com.vald3nir.diskwater.data.dto

import com.vald3nir.toolkit.data.dto.BaseDTO

class OrderItemDTO(
    var name: String? = null,
    var quantity: Int? = null,
    var unitValue: Float? = null,
) : BaseDTO()