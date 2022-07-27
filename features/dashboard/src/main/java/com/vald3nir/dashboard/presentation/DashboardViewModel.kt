package com.vald3nir.dashboard.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.vald3nir.commom.domain.dtos.OrderDTO
import com.vald3nir.commom.presentation.view.BaseViewModel
import com.vald3nir.core_ui.components.CustomListComponent

class DashboardViewModel : BaseViewModel() {

    private val _ordersSelected = MutableLiveData<MutableList<OrderDTO>>()
    val ordersSelected: LiveData<MutableList<OrderDTO>> = _ordersSelected

    private val ordersOpened = mutableListOf(
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f)
    )
    private val ordersProgress = mutableListOf(
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f)
    )
    private val ordersClosed = mutableListOf(
        OrderDTO(clientName = "Severino", address = "São Gerardo", total = 00.20f),
        OrderDTO(clientName = "Severino", address = "São Gerardo", total = 00.20f)
    )

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

    fun loadOrders() {
        _ordersSelected.postValue(ordersOpened)
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

}