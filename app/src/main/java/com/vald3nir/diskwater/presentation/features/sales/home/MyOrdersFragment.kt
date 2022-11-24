package com.vald3nir.diskwater.presentation.features.sales.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.core_repository.BaseDTO
import com.vald3nir.core_repository.baseDiffUtil
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.components.CustomListComponent
import com.vald3nir.core_ui.extensions.setupLayoutManager
import com.vald3nir.core_utils.extensions.toMoney
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.app.base.BaseFragment
import com.vald3nir.diskwater.databinding.FragmentMyOrdersBinding
import com.vald3nir.diskwater.databinding.OrderItemViewBinding
import com.vald3nir.diskwater.domain.dtos.OrderDTO
import com.vald3nir.diskwater.domain.dtos.OrderStatus
import com.vald3nir.diskwater.domain.toMutableBaseList
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOrdersFragment : BaseFragment() {

    private val viewModel: MyOrdersViewModel by viewModel()
    lateinit var binding: FragmentMyOrdersBinding
    private val mainCardAdapter by lazy {
        val adapter = CustomDifferAdapter(
            bindingInflater = OrderItemViewBinding::inflate,
            list = listOf(),
            itemDiffUtil = baseDiffUtil(),
            ::bindAdapter
        )
        adapter
    }

    private fun bindAdapter(
        baseDTO: BaseDTO,
        itemViewBinding: OrderItemViewBinding,
        position: Int,
        any: Any
    ) {
        if (baseDTO is OrderDTO) {
            itemViewBinding.apply {
                itemViewBinding.apply {
                    txtTitle.text = "Pedido ${baseDTO.date}"
                    txtSubtitle.text = baseDTO.address.toString()
                    txtValue.text = "${baseDTO.paymentType.title} - ${baseDTO.total.toMoney()}"
                }
            }
        }
    }

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            initViews()
            setupObservers()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLastOrders()
    }

    private fun FragmentMyOrdersBinding.initViews() {
        clcMyOrders.apply {
            setTabs(
                listOf(
                    CustomListComponent.CustomListTab(
                        title = "Pedido em aberto",
                        onTabSelectedListener = { viewModel.filterListByStatus(OrderStatus.OPEN) }),
                    CustomListComponent.CustomListTab(
                        title = "Pedido em andamento",
                        onTabSelectedListener = { viewModel.filterListByStatus(OrderStatus.PROGRESS) }),
                    CustomListComponent.CustomListTab(
                        title = "Pedido fechados",
                        onTabSelectedListener = { viewModel.filterListByStatus(OrderStatus.CLOSE) }),
                )
            )
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
    }

    private fun FragmentMyOrdersBinding.setupObservers() {
        mainCardAdapter.setOnItemClickListener(listener = { item, pos ->
            viewModel.cacheOrder(item as OrderDTO) // Valdenir ajusta tipo correto
            navigationToFragment(R.id.action_navigation_my_orders_to_navigation_order_detail)
        })

        viewModel.myOrders.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it.toMutableBaseList())
            clcMyOrders.notifyListSize(it.size)
        }
    }
}