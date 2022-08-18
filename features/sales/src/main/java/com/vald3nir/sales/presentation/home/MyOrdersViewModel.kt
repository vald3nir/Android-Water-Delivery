package com.vald3nir.sales.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.repository.dtos.OrderDTO
import com.vald3nir.repository.dtos.OrderStatus
import com.vald3nir.commom.presentation.view.BaseViewModel
import com.vald3nir.core_repository.firebase.FirebaseAuthenticator
import com.vald3nir.core_repository.firebase.FirebaseClient
import com.vald3nir.sales.domain.use_cases.OrderUseCase
import kotlinx.coroutines.launch

class MyOrdersViewModel(
    private val orderUseCase: OrderUseCase,
) : BaseViewModel() {

    private val firebaseClient = FirebaseClient()
    private val firebaseAuthenticator = FirebaseAuthenticator()

    private val _myOrders = MutableLiveData<List<com.vald3nir.repository.dtos.OrderDTO>>()
    val myOrders: LiveData<List<com.vald3nir.repository.dtos.OrderDTO>> = _myOrders

    private val _order = MutableLiveData<com.vald3nir.repository.dtos.OrderDTO>()
    val order: LiveData<com.vald3nir.repository.dtos.OrderDTO> = _order

    private var ordersOpen = arrayListOf<com.vald3nir.repository.dtos.OrderDTO>()
    private var ordersProgress = arrayListOf<com.vald3nir.repository.dtos.OrderDTO>()
    private var ordersClose = arrayListOf<com.vald3nir.repository.dtos.OrderDTO>()

    fun loadOrder() {
        _order.postValue(orderUseCase.loadCurrentOrder())
    }

    fun loadLastOrders() {
        viewModelScope.launch {
            val userID = firebaseAuthenticator.getUserID()
            firebaseClient.loadCollection(
                rootPath = "debug",
                document = "orders",
                collection = "orders",
                type = com.vald3nir.repository.dtos.OrderDTO::class.java,
                onSuccess = { allOrders ->
                    val orders = allOrders.filter { it.client?.userID == userID }
                    fillOrderType(orders.toMutableList())
                    _myOrders.postValue(ordersOpen)
                },
                onError = {}
            )
        }
    }

    fun filterListByStatus(status: com.vald3nir.repository.dtos.OrderStatus) {
        when (status) {
            com.vald3nir.repository.dtos.OrderStatus.OPEN -> _myOrders.postValue(ordersOpen)
            com.vald3nir.repository.dtos.OrderStatus.PROGRESS -> _myOrders.postValue(ordersProgress)
            com.vald3nir.repository.dtos.OrderStatus.CLOSE -> _myOrders.postValue(ordersClose)
        }
    }

    private fun fillOrderType(orders: MutableList<com.vald3nir.repository.dtos.OrderDTO>) {
        ordersOpen = arrayListOf()
        ordersProgress = arrayListOf()
        ordersClose = arrayListOf()
        orders.forEach {
            when (it.status) {
                com.vald3nir.repository.dtos.OrderStatus.OPEN -> ordersOpen.add(it)
                com.vald3nir.repository.dtos.OrderStatus.PROGRESS -> ordersProgress.add(it)
                com.vald3nir.repository.dtos.OrderStatus.CLOSE -> ordersClose.add(it)
            }
        }
    }

    fun cacheOrder(order: com.vald3nir.repository.dtos.OrderDTO) {
        orderUseCase.saveOrderInMemory(order)
    }

    fun clearCacheOrder() {
        orderUseCase.saveOrderInMemory(com.vald3nir.repository.dtos.OrderDTO())
    }
}