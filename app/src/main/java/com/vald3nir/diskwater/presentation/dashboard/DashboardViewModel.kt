package com.vald3nir.diskwater.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.vald3nir.diskwater.common.BaseViewModel
import com.vald3nir.diskwater.data.dto.OrderDTO
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import com.vald3nir.toolkit.componets.customviews.CustomListComponent

class DashboardViewModel(
    private val screenNavigation: ScreenNavigation,
    private val authUseCase: AuthUseCase,
) : BaseViewModel() {

    private val _ordersSelected = MutableLiveData<MutableList<OrderDTO>>()
    val ordersSelected: LiveData<MutableList<OrderDTO>> = _ordersSelected

    private val ordersOpened = mutableListOf(
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f)
    )
    private val ordersClosed = mutableListOf(
        OrderDTO(clientName = "Severino", address = "São Gerardo", total = 00.20f),
        OrderDTO(clientName = "Severino", address = "São Gerardo", total = 00.20f)
    )

    val tabsList = listOf(
        CustomListComponent.CustomListTab(title = "Pedidos abertos", onTabSelectedListener = {
            _ordersSelected.postValue(ordersOpened)
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
                return oldItem.equals(newItem)
            }
        }

    fun redirectToOrderDetail() {
        replaceFragment(FragmentEnum.ORDER_DETAIL)
    }


}