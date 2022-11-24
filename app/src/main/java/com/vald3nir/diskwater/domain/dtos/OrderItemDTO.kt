package com.vald3nir.diskwater.domain.dtos

import com.vald3nir.core_repository.BaseDTO

data class OrderItemDTO(
    var name: String? = null,
    var productID: String? = null,
    var quantity: Int? = null,
    var unitValue: Float? = null,
) : BaseDTO()