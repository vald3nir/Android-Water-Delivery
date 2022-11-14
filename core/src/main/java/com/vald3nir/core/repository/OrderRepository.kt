package com.vald3nir.core.repository

import com.vald3nir.core.repository.dtos.OrderDTO

interface OrderRepository {

    suspend fun requestOrder(
        orderDTO: OrderDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}