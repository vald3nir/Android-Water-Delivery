package com.vald3nir.diskwater.domain.usecases

import com.vald3nir.core_repository.firebase.FirebaseClient
import com.vald3nir.diskwater.domain.dtos.OrderDTO
import com.vald3nir.diskwater.domain.dtos.OrderStatus

class OrderManagerUseCaseImpl() : OrderManagerUseCase {

    private var currentOrder: OrderDTO? = null
    private val firebaseClient = FirebaseClient()

    override fun loadCurrentOrder(): OrderDTO? {
        return currentOrder
    }

    override fun saveOrderInMemory(order: OrderDTO?) {
        currentOrder = order
    }

    override suspend fun updateOrderStatus(status: OrderStatus) {
        currentOrder?.let {
            it.status = status
            firebaseClient.insertOrUpdateData(
                rootPath = "debug",
                document = "orders",
                collection = "orders",
                baseDTO = it,
                onSuccess = {},
                onError = {}
            )
        }
    }
}