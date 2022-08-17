package com.vald3nir.dashboard.domain

import com.vald3nir.commom.domain.dtos.OrderDTO
import com.vald3nir.commom.domain.dtos.OrderStatus
import com.vald3nir.core_repository.firebase.FirebaseClient

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