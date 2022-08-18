package com.vald3nir.base_repository.dtos

import com.vald3nir.core_repository.BaseDTO

data class OrderDTO(
    var isNew: Boolean = true,
    var client: ClientDTO? = null,
    var address: AddressDTO? = null,
    var date: String? = null,
    var items: List<OrderItemDTO> = listOf(),
    var total: Float = 0.0f,
    var paymentType: PaymentType = PaymentType.MONEY,
    var status: OrderStatus = OrderStatus.OPEN,
) : BaseDTO()