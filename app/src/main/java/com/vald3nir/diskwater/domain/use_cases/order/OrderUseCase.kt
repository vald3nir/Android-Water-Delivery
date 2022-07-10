package com.vald3nir.diskwater.domain.use_cases.order

import com.vald3nir.diskwater.data.dto.OrderItemDTO
import com.vald3nir.diskwater.data.dto.PaymentType
import com.vald3nir.diskwater.data.dto.ProductDTO

interface OrderUseCase {
    fun registerPaymentType(paymentType: PaymentType)
    fun loadPaymentType(): MutableList<PaymentType>
    fun registerItem(productDTO: ProductDTO, quantity: Int)
    fun getItemQuantity(productDTO: ProductDTO): String?
    fun calculateShoppingCartTotal(): Float
    fun loadItemsSelected(): MutableList<OrderItemDTO>
}