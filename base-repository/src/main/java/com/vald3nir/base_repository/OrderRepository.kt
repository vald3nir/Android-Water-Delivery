package com.vald3nir.base_repository

import com.vald3nir.base_repository.dtos.OrderDTO

interface OrderRepository {

    suspend fun requestOrder(
        orderDTO: OrderDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}