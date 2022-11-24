package com.vald3nir.diskwater.presentation.features.sales.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.core_repository.firebase.FirebaseAuthenticator
import com.vald3nir.core_repository.firebase.FirebaseClient
import com.vald3nir.diskwater.app.base.BaseViewModel
import com.vald3nir.diskwater.domain.dtos.OrderDTO
import com.vald3nir.diskwater.domain.dtos.OrderStatus
import com.vald3nir.diskwater.domain.usecases.OrderUseCase
import kotlinx.coroutines.launch

class MyOrdersViewModel(
    private val orderUseCase: OrderUseCase,
) : BaseViewModel() {

    private val firebaseClient = FirebaseClient()
    private val firebaseAuthenticator = FirebaseAuthenticator()

    private val _myOrders = MutableLiveData<List<OrderDTO>>()
    val myOrders: LiveData<List<OrderDTO>> = _myOrders

    private val _order = MutableLiveData<OrderDTO>()
    val order: LiveData<OrderDTO> = _order

    private var ordersOpen = arrayListOf<OrderDTO>()
    private var ordersProgress = arrayListOf<OrderDTO>()
    private var ordersClose = arrayListOf<OrderDTO>()

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
                type = OrderDTO::class.java,
                onSuccess = { allOrders ->
                    val orders = allOrders.filter { it.client?.userID == userID }
                    fillOrderType(orders.toMutableList())
                    _myOrders.postValue(ordersOpen)
                },
                onError = {}
            )
        }
    }

    fun filterListByStatus(status: OrderStatus) {
        when (status) {
            OrderStatus.OPEN -> _myOrders.postValue(ordersOpen)
            OrderStatus.PROGRESS -> _myOrders.postValue(ordersProgress)
            OrderStatus.CLOSE -> _myOrders.postValue(ordersClose)
        }
    }

    private fun fillOrderType(orders: MutableList<OrderDTO>) {
        ordersOpen = arrayListOf()
        ordersProgress = arrayListOf()
        ordersClose = arrayListOf()
        orders.forEach {
            when (it.status) {
                OrderStatus.OPEN -> ordersOpen.add(it)
                OrderStatus.PROGRESS -> ordersProgress.add(it)
                OrderStatus.CLOSE -> ordersClose.add(it)
            }
        }
    }

    fun cacheOrder(order: OrderDTO) {
        orderUseCase.saveOrderInMemory(order)
    }

    fun clearCacheOrder() {
        orderUseCase.saveOrderInMemory(OrderDTO())
    }
}