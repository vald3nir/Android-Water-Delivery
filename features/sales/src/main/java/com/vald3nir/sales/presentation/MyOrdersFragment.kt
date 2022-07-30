package com.vald3nir.sales.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.commom.domain.dtos.OrderDTO
import com.vald3nir.commom.domain.utils.toMutableBaseList
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.dashboard.databinding.OrderItemViewBinding
import com.vald3nir.repository.BaseDTO
import com.vald3nir.repository.baseDiffUtil
import com.vald3nir.sales.databinding.FragmentMyOrdersBinding
import com.vald3nir.utils.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOrdersFragment : BaseFragment() {

    private val viewModel: SalesViewModel by viewModel()
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
                    txtTitle.text = baseDTO.clientName
                    txtSubtitle.text = baseDTO.address
                    txtValue.text = baseDTO.total.toMoney()
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
        binding.initViews()
        return binding.root
    }

    private fun FragmentMyOrdersBinding.initViews() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding.setupObservers()
        this.viewModel.loadLastOrders()
    }

    private fun FragmentMyOrdersBinding.setupObservers() {

        mainCardAdapter.setOnItemClickListener(listener = { item, pos ->
        })

        viewModel.myOrders.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it.toMutableBaseList())
            clcMyOrders.notifyListSize(it.size)
        }
    }
}