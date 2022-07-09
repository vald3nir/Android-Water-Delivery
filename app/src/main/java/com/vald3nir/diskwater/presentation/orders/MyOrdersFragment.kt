package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.OrderDTO
import com.vald3nir.diskwater.databinding.FragmentMyOrdersBinding
import com.vald3nir.diskwater.databinding.OrderItemViewBinding
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.diskwater.domain.utils.toMutableBaseList
import com.vald3nir.toolkit.core.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.data.dto.BaseDTO
import com.vald3nir.toolkit.data.dto.baseDiffUtil
import com.vald3nir.toolkit.utils.extensions.setupToolbar
import com.vald3nir.toolkit.utils.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOrdersFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentMyOrdersBinding
    private val mainCardAdapter by lazy {
        val adapter = CustomListAdapterDiffer(
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
        toolbar.setupToolbar(
            activity = activity,
            title = getString(R.string.my_last_orders),
            showBackButton = false,
        )
        btnNewOrder.setup(
            title = R.string.new_order,
            clickListener = {
                viewModel.replaceFragment(FragmentEnum.CONFIRM_ADDRESS)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding.setupObservers()
        this.binding.btnNewOrder.showLoading(true)
        this.viewModel.loadLastOrders()
    }

    private fun FragmentMyOrdersBinding.setupObservers() {

        mainCardAdapter.setOnItemClickListener(listener = { item, pos ->
            viewModel.replaceFragment(FragmentEnum.ORDER_DETAIL, item)
        })

        viewModel.myOrders.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it.toMutableBaseList())
            clcMyOrders.notifyListSize(it.size)
            btnNewOrder.showLoading(false)
        }
    }
}