package com.vald3nir.diskwater.domain.repository

import com.vald3nir.diskwater.domain.dtos.OrderDTO

interface OrderRepository {

    suspend fun requestOrder(
        orderDTO: OrderDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}