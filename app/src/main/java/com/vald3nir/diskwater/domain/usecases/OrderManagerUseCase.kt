package com.vald3nir.diskwater.domain.usecases

import com.vald3nir.diskwater.domain.dtos.OrderDTO
import com.vald3nir.diskwater.domain.dtos.OrderStatus

interface OrderManagerUseCase {
    fun saveOrderInMemory(order: OrderDTO?)
    fun loadCurrentOrder(): OrderDTO?
    suspend fun updateOrderStatus(status: OrderStatus)
}