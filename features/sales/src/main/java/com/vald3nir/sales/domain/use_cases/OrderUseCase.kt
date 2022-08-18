package com.vald3nir.sales.domain.use_cases

import com.vald3nir.repository.dtos.AddressDTO
import com.vald3nir.repository.dtos.OrderDTO
import com.vald3nir.repository.dtos.PaymentType
import com.vald3nir.repository.dtos.ProductDTO

interface OrderUseCase {
    fun addPaymentType(paymentType: com.vald3nir.repository.dtos.PaymentType)
    fun registerItem(productDTO: com.vald3nir.repository.dtos.ProductDTO, quantity: Int)
    fun putAddress(address: com.vald3nir.repository.dtos.AddressDTO)
    fun getItemQuantity(productDTO: com.vald3nir.repository.dtos.ProductDTO): String?
    fun loadCurrentOrder(): com.vald3nir.repository.dtos.OrderDTO
    fun saveOrderInMemory(order: com.vald3nir.repository.dtos.OrderDTO)
    suspend fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit)
}