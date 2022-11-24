package com.vald3nir.diskwater.domain.usecases

import com.vald3nir.diskwater.domain.dtos.AddressDTO
import com.vald3nir.diskwater.domain.dtos.OrderDTO
import com.vald3nir.diskwater.domain.dtos.PaymentType
import com.vald3nir.diskwater.domain.dtos.ProductDTO

interface OrderUseCase {
    fun addPaymentType(paymentType: PaymentType)
    fun registerItem(productDTO: ProductDTO, quantity: Int)
    fun putAddress(address: AddressDTO)
    fun getItemQuantity(productDTO: ProductDTO): String?
    fun loadCurrentOrder(): OrderDTO
    fun saveOrderInMemory(order: OrderDTO)
    suspend fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit)
}