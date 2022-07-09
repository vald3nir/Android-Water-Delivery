package com.vald3nir.diskwater.domain.use_cases.order

import com.vald3nir.diskwater.data.dto.OrderItemDTO
import com.vald3nir.diskwater.data.dto.ProductDTO

interface OrderUseCase {
    suspend fun registerItem(productDTO: ProductDTO, quantity: Int)
    fun getItemQuantity(productDTO: ProductDTO): String?
    suspend fun calculateShoppingCartTotal(): Float
    suspend fun loadItemsSelected(): MutableList<OrderItemDTO>
}