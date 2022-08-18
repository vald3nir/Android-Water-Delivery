package com.vald3nir.sales.domain.use_cases

import com.vald3nir.repository.OrderRepository

class OrderUseCaseImpl(
    private val repository: OrderRepository
) : OrderUseCase {

    private var shoppingCartMap = mutableMapOf<String, com.vald3nir.repository.dtos.OrderItemDTO>()
    private var currentOrder = com.vald3nir.repository.dtos.OrderDTO()

    override fun addPaymentType(paymentType: com.vald3nir.repository.dtos.PaymentType) {
        this.currentOrder.paymentType = paymentType
    }

    override fun loadCurrentOrder(): com.vald3nir.repository.dtos.OrderDTO {
        return currentOrder
    }

    override fun saveOrderInMemory(order: com.vald3nir.repository.dtos.OrderDTO) {
        currentOrder = order
    }

    override fun putAddress(address: com.vald3nir.repository.dtos.AddressDTO) {
        currentOrder.address = address
    }

    override fun registerItem(productDTO: com.vald3nir.repository.dtos.ProductDTO, quantity: Int) {
        shoppingCartMap[productDTO.uid] = com.vald3nir.repository.dtos.OrderItemDTO(
            name = productDTO.name,
            productID = productDTO.uid,
            quantity = quantity,
            unitValue = productDTO.price
        )
        currentOrder.total = calculateShoppingCartTotal()
        currentOrder.items = loadItemsSelected()
    }

    private fun loadItemsSelected(): MutableList<com.vald3nir.repository.dtos.OrderItemDTO> {
        val items = mutableListOf<com.vald3nir.repository.dtos.OrderItemDTO>()
        shoppingCartMap.map {
            if ((it.value.quantity ?: 0) > 0) {
                items.add(it.value)
            }
        }
        return items
    }

    private fun calculateShoppingCartTotal(): Float {
        var total = 0.0f
        for (key in shoppingCartMap.keys) {
            val item = shoppingCartMap[key]
            val quantity: Int = item?.quantity ?: 0
            val unitValue: Float = item?.unitValue ?: 0.0f
            total += (quantity * unitValue)
        }
        return total
    }

    override fun getItemQuantity(productDTO: com.vald3nir.repository.dtos.ProductDTO): String? {
        return shoppingCartMap[productDTO.uid]?.quantity?.toString()
    }

    override suspend fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit) {
        repository.requestOrder(currentOrder, onSuccess = {
            currentOrder = com.vald3nir.repository.dtos.OrderDTO()
            shoppingCartMap = mutableMapOf()
            onSuccess.invoke()
        }, onError)
    }
}