package com.vald3nir.repository

import com.vald3nir.repository.dtos.OrderDTO

interface OrderRepository {

    suspend fun requestOrder(
        orderDTO: OrderDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}