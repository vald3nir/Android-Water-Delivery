package com.vald3nir.sales.domain.use_cases

import com.vald3nir.commom.domain.dtos.AddressDTO
import com.vald3nir.commom.domain.dtos.OrderDTO
import com.vald3nir.commom.domain.dtos.PaymentType
import com.vald3nir.commom.domain.dtos.ProductDTO

interface OrderUseCase {
    fun addPaymentType(paymentType: PaymentType)
    fun registerItem(productDTO: ProductDTO, quantity: Int)
    fun putAddress(address: AddressDTO)
    fun getItemQuantity(productDTO: ProductDTO): String?
    fun loadCurrentOrder(): OrderDTO
    fun saveOrderInMemory(order: OrderDTO)
    suspend fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit)
}