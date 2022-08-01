package com.vald3nir.dashboard.presentation.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.Nullable
import com.vald3nir.commom.domain.dtos.OrderItemDTO
import com.vald3nir.commom.domain.dtos.OrderStatus
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.extensions.setupLayoutManager
import com.vald3nir.dashboard.R
import com.vald3nir.dashboard.databinding.FragmentClientOrderDetailBinding
import com.vald3nir.dashboard.databinding.ItemOrderDetailBinding
import com.vald3nir.repository.baseDiffUtil
import com.vald3nir.utils.extensions.format
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClientOrderDetailFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModel()
    lateinit var binding: FragmentClientOrderDetailBinding
    private val mainCardAdapter = CustomDifferAdapter(
        bindingInflater = ItemOrderDetailBinding::inflate,
        list = listOf(),
        itemDiffUtil = baseDiffUtil(),
        onBind = { orderItemDTO, itemViewBinding, _, _ ->
            bindAdapter(itemViewBinding, orderItemDTO)
        }
    )

    @SuppressLint("SetTextI18n")
    private fun bindAdapter(
        itemViewBinding: ItemOrderDetailBinding,
        orderItemDTO: OrderItemDTO
    ) {
        itemViewBinding.apply {
            txvTitle.text = orderItemDTO.name
            txvDetail.text = "${orderItemDTO.quantity} x ${orderItemDTO.unitValue.format()}"
        }
    }

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigationToFragment(R.id.action_clientOrderDetailFragment_to_navigation_list_orders)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            initViews()
            setupObservers()
        }
        viewModel.loadOrder()
    }

    private fun FragmentClientOrderDetailBinding.initViews() {
        clcOrderDetail.apply {
            setTab("Itens do Pedido")
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
    }

    private fun FragmentClientOrderDetailBinding.setupObservers() {

        rbOrderStatus.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_open -> {
                    viewModel.updateOrderStatus(OrderStatus.OPEN)
                }
                R.id.rb_progress -> {
                    viewModel.updateOrderStatus(OrderStatus.PROGRESS)
                }
                R.id.rb_closed -> {
                    viewModel.updateOrderStatus(OrderStatus.CLOSE)
                }
            }
        }


        viewModel.order.observe(viewLifecycleOwner) { order ->
            order.apply {
                address.let {
                    txvAddressDetail.text = it.toString()
                }
                total.let { value ->
                    txvTotalValue.text = value.format()
                }
                items.let {
                    mainCardAdapter.submitList(it.toMutableList())
                    clcOrderDetail.notifyListSize(it.size)
                }
                txvPaymentType.text = order.paymentType.title

                when (order.status) {
                    OrderStatus.OPEN -> {
                        rbOpen.isChecked = true
                    }
                    OrderStatus.CLOSE -> {
                        rbClosed.isChecked = true
                    }
                    OrderStatus.PROGRESS -> {
                        rbProgress.isChecked = true
                    }
                }
            }
        }
    }
}