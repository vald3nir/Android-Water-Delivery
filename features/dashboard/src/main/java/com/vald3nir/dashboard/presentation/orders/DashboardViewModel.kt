package com.vald3nir.dashboard.presentation.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.vald3nir.repository.dtos.OrderDTO
import com.vald3nir.repository.dtos.OrderStatus
import com.vald3nir.commom.presentation.view.BaseViewModel
import com.vald3nir.core_ui.components.CustomListComponent
import com.vald3nir.dashboard.domain.OrderManagerUseCase
import com.vald3nir.core_repository.firebase.FirebaseClient
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val orderManagerUseCase: OrderManagerUseCase
) : BaseViewModel() {

    private val _ordersSelected = MutableLiveData<List<com.vald3nir.repository.dtos.OrderDTO>>()
    val ordersSelected: LiveData<List<com.vald3nir.repository.dtos.OrderDTO>> = _ordersSelected

    private val firebaseClient = FirebaseClient()

    private val _order = MutableLiveData<com.vald3nir.repository.dtos.OrderDTO>()
    val order: LiveData<com.vald3nir.repository.dtos.OrderDTO> = _order

    private var ordersOpened = arrayListOf<com.vald3nir.repository.dtos.OrderDTO>()
    private var ordersProgress = arrayListOf<com.vald3nir.repository.dtos.OrderDTO>()
    private var ordersClosed = arrayListOf<com.vald3nir.repository.dtos.OrderDTO>()

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

    fun saveOrderInMemory(orderDTO: com.vald3nir.repository.dtos.OrderDTO) {
        orderManagerUseCase.saveOrderInMemory(orderDTO)
    }

    fun loadOrders() {
        viewModelScope.launch {

            firebaseClient.loadCollection(
                rootPath = "debug",
                document = "orders",
                collection = "orders",
                type = com.vald3nir.repository.dtos.OrderDTO::class.java,
                onSuccess = {
                    fillOrderType(it)
                    _ordersSelected.postValue(ordersOpened)
                },
                onError = {}
            )
        }
    }

    private fun fillOrderType(orders: MutableList<com.vald3nir.repository.dtos.OrderDTO>) {
        ordersOpened = arrayListOf()
        ordersProgress = arrayListOf()
        ordersClosed = arrayListOf()
        orders.forEach {
            when (it.status) {
                com.vald3nir.repository.dtos.OrderStatus.OPEN -> ordersOpened.add(it)
                com.vald3nir.repository.dtos.OrderStatus.PROGRESS -> ordersProgress.add(it)
                com.vald3nir.repository.dtos.OrderStatus.CLOSE -> ordersClosed.add(it)
            }
        }
    }

    fun orderDiffUtil(): DiffUtil.ItemCallback<com.vald3nir.repository.dtos.OrderDTO> =
        object : DiffUtil.ItemCallback<com.vald3nir.repository.dtos.OrderDTO>() {

            override fun areItemsTheSame(
                oldItem: com.vald3nir.repository.dtos.OrderDTO,
                newItem: com.vald3nir.repository.dtos.OrderDTO
            ): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(
                oldItem: com.vald3nir.repository.dtos.OrderDTO,
                newItem: com.vald3nir.repository.dtos.OrderDTO
            ): Boolean {
                return oldItem == newItem
            }
        }

    fun updateOrderStatus(status: com.vald3nir.repository.dtos.OrderStatus) {
        viewModelScope.launch {
            orderManagerUseCase.updateOrderStatus(status)
        }
    }

}