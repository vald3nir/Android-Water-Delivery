package com.vald3nir.sales.domain.use_cases

import com.vald3nir.commom.domain.dtos.OrderItemDTO
import com.vald3nir.commom.domain.dtos.PaymentType
import com.vald3nir.commom.domain.dtos.ProductDTO

interface OrderUseCase {
    fun addPaymentType(paymentType: PaymentType)
    fun registerItem(productDTO: ProductDTO, quantity: Int)
    fun getItemQuantity(productDTO: ProductDTO): String?
    fun calculateShoppingCartTotal(): Float
    fun loadItemsSelected(): MutableList<OrderItemDTO>
    suspend fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit)
}