package com.vald3nir.sales.domain.use_cases

import com.vald3nir.commom.domain.dtos.OrderDTO
import com.vald3nir.commom.domain.dtos.OrderItemDTO
import com.vald3nir.commom.domain.dtos.PaymentType
import com.vald3nir.commom.domain.dtos.ProductDTO

class OrderUseCaseImpl : OrderUseCase {

    private var shoppingCartMap = mutableMapOf<String, OrderItemDTO>()
    private var paymentType = PaymentType.MONEY

    override fun addPaymentType(paymentType: PaymentType) {
        this.paymentType = paymentType
    }

    override fun registerItem(productDTO: ProductDTO, quantity: Int) {
        shoppingCartMap[productDTO.uid] = OrderItemDTO(
            name = productDTO.name,
            quantity = quantity,
            unitValue = productDTO.price
        )
    }

    override fun getItemQuantity(productDTO: ProductDTO): String? {
        return shoppingCartMap[productDTO.uid]?.quantity?.toString()
    }

    override fun calculateShoppingCartTotal(): Float {
        var total = 0.0f
        for (key in shoppingCartMap.keys) {
            val item = shoppingCartMap[key]
            val quantity: Int = item?.quantity ?: 0
            val unitValue: Float = item?.unitValue ?: 0.0f
            total += (quantity * unitValue)
        }
        return total
    }

    override fun loadItemsSelected(): MutableList<OrderItemDTO> {
        val items = mutableListOf<OrderItemDTO>()
        shoppingCartMap.map {
            if ((it.value.quantity ?: 0) > 0) {
                items.add(it.value)
            }
        }
        return items
    }

    override suspend fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit) {

        val order = OrderDTO(
            clientName = null,
            address = null,
            date = null,
            items = null,
            total = 0.0f,
            paymentType = paymentType,
        )

    }
}