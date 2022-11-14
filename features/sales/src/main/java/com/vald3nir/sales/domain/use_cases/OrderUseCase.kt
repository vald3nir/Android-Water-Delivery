package com.vald3nir.sales.domain.use_cases

interface OrderUseCase {
    fun addPaymentType(paymentType: com.vald3nir.core.repository.dtos.PaymentType)
    fun registerItem(productDTO: com.vald3nir.core.repository.dtos.ProductDTO, quantity: Int)
    fun putAddress(address: com.vald3nir.core.repository.dtos.AddressDTO)
    fun getItemQuantity(productDTO: com.vald3nir.core.repository.dtos.ProductDTO): String?
    fun loadCurrentOrder(): com.vald3nir.core.repository.dtos.OrderDTO
    fun saveOrderInMemory(order: com.vald3nir.core.repository.dtos.OrderDTO)
    suspend fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit)
}