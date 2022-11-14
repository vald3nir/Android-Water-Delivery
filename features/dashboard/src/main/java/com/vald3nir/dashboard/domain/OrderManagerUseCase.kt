package com.vald3nir.dashboard.domain

interface OrderManagerUseCase {
    fun saveOrderInMemory(order: com.vald3nir.core.repository.dtos.OrderDTO?)
    fun loadCurrentOrder(): com.vald3nir.core.repository.dtos.OrderDTO?
    suspend fun updateOrderStatus(status: com.vald3nir.core.repository.dtos.OrderStatus)
}