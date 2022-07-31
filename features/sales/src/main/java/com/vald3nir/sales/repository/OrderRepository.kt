package com.vald3nir.sales.repository

import com.vald3nir.commom.domain.dtos.OrderDTO

interface OrderRepository {

    suspend fun requestOrder(
        orderDTO: OrderDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}