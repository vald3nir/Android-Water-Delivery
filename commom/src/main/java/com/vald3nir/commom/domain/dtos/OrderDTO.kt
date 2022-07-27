package com.vald3nir.commom.domain.dtos

import com.vald3nir.repository.BaseDTO

data class OrderDTO(
    var isNew: Boolean = true,
    var clientName: String? = null,
    var address: String? = null,
    var date: String? = null,
    var items: List<OrderItemDTO>? = null,
    var total: Float = 0.0f,
    var paymentType: PaymentType = PaymentType.MONEY,
) : BaseDTO()