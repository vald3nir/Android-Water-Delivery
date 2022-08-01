package com.vald3nir.dashboard.domain

import com.vald3nir.commom.domain.dtos.OrderDTO
import com.vald3nir.commom.domain.dtos.OrderStatus

interface OrderManagerUseCase {
    fun saveOrderInMemory(order: OrderDTO?)
    fun loadCurrentOrder(): OrderDTO?
    suspend fun updateOrderStatus(status: OrderStatus)
}