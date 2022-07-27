package com.vald3nir.dashboard.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.commom.domain.dtos.OrderDTO
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.extensions.setupLayoutManager
import com.vald3nir.dashboard.databinding.FragmentListOrdersBinding
import com.vald3nir.dashboard.databinding.OrderItemViewBinding
import com.vald3nir.utils.extensions.toMoney
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
        order: OrderDTO,
        itemViewBinding: OrderItemViewBinding,
        position: Int,
        any: Any
    ) {
        itemViewBinding.apply {
            txtTitle.text = order.clientName
            txtValue.text = order.total.toMoney()
            txtSubtitle.text = order.address
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
        binding.initViews()
        binding.setupObservers()
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
        })

        viewModel.ordersSelected.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it)
            clcOrdersOpen.notifyListSize(it.size)
        }
    }

}

