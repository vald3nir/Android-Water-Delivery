package com.vald3nir.dashboard.domain

import com.vald3nir.core_repository.firebase.FirebaseClient

class OrderManagerUseCaseImpl() : OrderManagerUseCase {

    private var currentOrder: com.vald3nir.base_repository.dtos.OrderDTO? = null
    private val firebaseClient = FirebaseClient()

    override fun loadCurrentOrder(): com.vald3nir.base_repository.dtos.OrderDTO? {
        return currentOrder
    }

    override fun saveOrderInMemory(order: com.vald3nir.base_repository.dtos.OrderDTO?) {
        currentOrder = order
    }

    override suspend fun updateOrderStatus(status: com.vald3nir.base_repository.dtos.OrderStatus) {
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