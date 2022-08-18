package com.vald3nir.dashboard.domain

import com.vald3nir.repository.dtos.OrderDTO
import com.vald3nir.repository.dtos.OrderStatus

interface OrderManagerUseCase {
    fun saveOrderInMemory(order: com.vald3nir.repository.dtos.OrderDTO?)
    fun loadCurrentOrder(): com.vald3nir.repository.dtos.OrderDTO?
    suspend fun updateOrderStatus(status: com.vald3nir.repository.dtos.OrderStatus)
}