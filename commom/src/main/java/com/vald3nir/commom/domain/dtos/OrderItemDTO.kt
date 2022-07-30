package com.vald3nir.commom.domain.dtos

import com.vald3nir.repository.BaseDTO

data class OrderItemDTO(
    var name: String? = null,
    var quantity: Int? = null,
    var unitValue: Float? = null,
) : BaseDTO()