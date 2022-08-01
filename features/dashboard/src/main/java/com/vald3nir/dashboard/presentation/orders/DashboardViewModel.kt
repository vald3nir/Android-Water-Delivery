package com.vald3nir.dashboard.presentation.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.vald3nir.commom.domain.dtos.OrderDTO
import com.vald3nir.commom.domain.dtos.OrderStatus
import com.vald3nir.commom.presentation.view.BaseViewModel
import com.vald3nir.core_ui.components.CustomListComponent
import com.vald3nir.dashboard.domain.OrderManagerUseCase
import com.vald3nir.repository.firebase.FirebaseClient
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val orderManagerUseCase: OrderManagerUseCase
) : BaseViewModel() {

    private val _ordersSelected = MutableLiveData<List<OrderDTO>>()
    val ordersSelected: LiveData<List<OrderDTO>> = _ordersSelected

    private val firebaseClient = FirebaseClient()

    private val _order = MutableLiveData<OrderDTO>()
    val order: LiveData<OrderDTO> = _order

    private var ordersOpened = arrayListOf<OrderDTO>()
    private var ordersProgress = arrayListOf<OrderDTO>()
    private var ordersClosed = arrayListOf<OrderDTO>()

    val tabsList = listOf(
        CustomListComponent.CustomListTab(title = "Abertos", onTabSelectedListener = {
            _ordersSelected.postValue(ordersOpened)
        }),
        CustomListComponent.CustomListTab(title = "Andamento", onTabSelectedListener = {
            _ordersSelected.postValue(ordersProgress)
        }),
        CustomListComponent.CustomListTab(title = "Fechados", onTabSelectedListener = {
            _ordersSelected.postValue(ordersClosed)
        }),
    )

    fun loadOrder() {
        _order.postValue(orderManagerUseCase.loadCurrentOrder())
    }

    fun saveOrderInMemory(orderDTO: OrderDTO) {
        orderManagerUseCase.saveOrderInMemory(orderDTO)
    }

    fun loadOrders() {
        viewModelScope.launch {

            firebaseClient.loadCollection(
                rootPath = "debug",
                document = "orders",
                collection = "orders",
                type = OrderDTO::class.java,
                onSuccess = {
                    fillOrderType(it)
                    _ordersSelected.postValue(ordersOpened)
                },
                onError = {}
            )
        }
    }

    private fun fillOrderType(orders: MutableList<OrderDTO>) {
        ordersOpened = arrayListOf()
        ordersProgress = arrayListOf()
        ordersClosed = arrayListOf()
        orders.forEach {
            when (it.status) {
                OrderStatus.OPEN -> ordersOpened.add(it)
                OrderStatus.PROGRESS -> ordersProgress.add(it)
                OrderStatus.CLOSE -> ordersClosed.add(it)
            }
        }
    }

    fun orderDiffUtil(): DiffUtil.ItemCallback<OrderDTO> =
        object : DiffUtil.ItemCallback<OrderDTO>() {

            override fun areItemsTheSame(
                oldItem: OrderDTO,
                newItem: OrderDTO
            ): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(
                oldItem: OrderDTO,
                newItem: OrderDTO
            ): Boolean {
                return oldItem == newItem
            }
        }

    fun updateOrderStatus(status: OrderStatus) {
        viewModelScope.launch {
            orderManagerUseCase.updateOrderStatus(status)
        }
    }

}