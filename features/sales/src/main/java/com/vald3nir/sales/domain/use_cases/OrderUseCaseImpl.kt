package com.vald3nir.sales.domain.use_cases

import com.vald3nir.commom.domain.dtos.*
import com.vald3nir.sales.repository.OrderRepository

class OrderUseCaseImpl(
    private val repository: OrderRepository
) : OrderUseCase {

    private var shoppingCartMap = mutableMapOf<String, OrderItemDTO>()
    private var currentOrder = OrderDTO()

    override fun addPaymentType(paymentType: PaymentType) {
        this.currentOrder.paymentType = paymentType
    }

    override fun loadCurrentOrder(): OrderDTO {
        return currentOrder
    }

    override fun saveOrderInMemory(order: OrderDTO) {
        currentOrder = order
    }

    override fun putAddress(address: AddressDTO) {
        currentOrder.address = address
    }

    override fun registerItem(productDTO: ProductDTO, quantity: Int) {
        shoppingCartMap[productDTO.uid] = OrderItemDTO(
            name = productDTO.name,
            productID = productDTO.uid,
            quantity = quantity,
            unitValue = productDTO.price
        )
        currentOrder.total = calculateShoppingCartTotal()
        currentOrder.items = loadItemsSelected()
    }

    private fun loadItemsSelected(): MutableList<OrderItemDTO> {
        val items = mutableListOf<OrderItemDTO>()
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

    override fun getItemQuantity(productDTO: ProductDTO): String? {
        return shoppingCartMap[productDTO.uid]?.quantity?.toString()
    }

    override suspend fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit) {
        repository.requestOrder(currentOrder, onSuccess = {
            currentOrder = OrderDTO()
            shoppingCartMap = mutableMapOf()
            onSuccess.invoke()
        }, onError)
    }
}