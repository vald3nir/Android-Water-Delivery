package com.vald3nir.diskwater.domain.use_cases.order

import com.vald3nir.diskwater.data.dto.OrderItemDTO
import com.vald3nir.diskwater.data.dto.ProductDTO

class OrderUseCaseImpl : OrderUseCase {

    private var shoppingCartMap = mutableMapOf<String, OrderItemDTO>()

    override suspend fun registerItem(productDTO: ProductDTO, quantity: Int) {
        shoppingCartMap[productDTO.uid] = OrderItemDTO(
            name = productDTO.name,
            quantity = quantity,
            unitValue = productDTO.price
        )
    }

    override fun getItemQuantity(productDTO: ProductDTO): String? {
        return shoppingCartMap[productDTO.uid]?.quantity?.toString()
    }

    override suspend fun calculateShoppingCartTotal(): Float {
        var total = 0.0f
        for (key in shoppingCartMap.keys) {
            val item = shoppingCartMap[key]
            val quantity: Int = item?.quantity ?: 0
            val unitValue: Float = item?.unitValue ?: 0.0f
            total += (quantity * unitValue)
        }
        return total
    }

    override suspend fun loadItemsSelected(): MutableList<OrderItemDTO> {
        val items = mutableListOf<OrderItemDTO>()
        shoppingCartMap.map {
            if ((it.value.quantity ?: 0) > 0) {
                items.add(it.value)
            }
        }
        return items
    }
}