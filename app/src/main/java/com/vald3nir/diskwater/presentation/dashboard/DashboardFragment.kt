package com.vald3nir.diskwater.presentation.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.vald3nir.diskwater.common.componets.CustomSheetDialog
import com.vald3nir.diskwater.common.componets.SingleAsyncTypeGenericAdapterDiffer
import com.vald3nir.diskwater.common.core.BaseFragment
import com.vald3nir.diskwater.common.extensions.setup
import com.vald3nir.diskwater.common.extensions.setupToolbar
import com.vald3nir.diskwater.common.extensions.toMoney
import com.vald3nir.diskwater.data.dto.OrderDTO
import com.vald3nir.diskwater.databinding.FragmentDashboardBinding
import com.vald3nir.diskwater.databinding.OrderItemViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModel()
    lateinit var binding: FragmentDashboardBinding

    private val orders = mutableListOf(
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
        OrderDTO(clientName = "Valdenir", address = "São Gerardo", total = 125.20f),
    )

    private object OrderDiffUtil : DiffUtil.ItemCallback<OrderDTO>() {
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

    private val mainCardAdapter by lazy {
        val adapter = SingleAsyncTypeGenericAdapterDiffer(
            bindingInflater = OrderItemViewBinding::inflate,
            list = orders,
            itemDiffUtil = OrderDiffUtil,
            ::bindAdapter
        )
        binding.rcvOrders.adapter = adapter
        adapter
    }

    private fun bindAdapter(
        order: OrderDTO,
        itemViewBinding: OrderItemViewBinding,
        position: Int,
        any: Any
    ) {
        itemViewBinding.apply {
            txtClientName.text = order.clientName
            txtValue.text = order.total.toMoney()
            txtDistrict.text = order.address
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        viewModel.appView = appView
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                title = "Minhas entregas",
                showBackButton = false,
                menuClickListener = { activity?.let { openMenu(it) } }
            )

            rcvOrders.setup()
            mainCardAdapter.submitList(orders)
        }
    }



    private fun openMenu(context: Context) {
        val dialog = CustomSheetDialog(context, viewModel.getMenuItems())
        dialog.setCancelable(true)
        dialog.show()
    }
}