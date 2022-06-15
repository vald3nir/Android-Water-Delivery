package com.vald3nir.diskwater.data.dto

import com.vald3nir.toolkit.data.dto.BaseDTO

class OrderItemDTO(
    var name: String? = null,
    var amount: Int = 0,
    var value: Float = 0.0f,
) : BaseDTO()