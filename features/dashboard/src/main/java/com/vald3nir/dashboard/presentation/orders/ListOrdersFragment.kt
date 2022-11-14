package com.vald3nir.dashboard.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.core.presentation.BaseFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.extensions.setupLayoutManager
import com.vald3nir.dashboard.R
import com.vald3nir.dashboard.databinding.FragmentListOrdersBinding
import com.vald3nir.dashboard.databinding.OrderItemViewBinding
import com.vald3nir.core_utils.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListOrdersFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModel()
    lateinit var binding: FragmentListOrdersBinding

    private val mainCardAdapter by lazy {
        val adapter = CustomDifferAdapter(
            bindingInflater = OrderItemViewBinding::inflate,
            list = listOf(),
            itemDiffUtil = viewModel.orderDiffUtil(),
            ::bindAdapter
        )
        adapter
    }

    private fun bindAdapter(
        orderDTO: com.vald3nir.core.repository.dtos.OrderDTO,
        itemViewBinding: OrderItemViewBinding,
        position: Int,
        any: Any
    ) {
        itemViewBinding.apply {
            txtTitle.text = "Pedido ${orderDTO.date}"
            txtSubtitle.text = orderDTO.address.toString()
            txtValue.text = "${orderDTO.paymentType.title} - ${orderDTO.total.toMoney()}"
        }
    }

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOrdersBinding.inflate(inflater, container, false)
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
        viewModel.loadOrders()
    }

    private fun FragmentListOrdersBinding.initViews() {
        clcOrdersOpen.apply {
            setTabs(viewModel.tabsList)
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
    }

    private fun FragmentListOrdersBinding.setupObservers() {

        mainCardAdapter.setOnItemClickListener(listener = { item, pos ->
            viewModel.saveOrderInMemory(item)
            navigationToFragment(R.id.action_navigation_list_orders_to_clientOrderDetailFragment)
        })

        viewModel.ordersSelected.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it.toMutableList())
            clcOrdersOpen.notifyListSize(it.size)
        }
    }

}

