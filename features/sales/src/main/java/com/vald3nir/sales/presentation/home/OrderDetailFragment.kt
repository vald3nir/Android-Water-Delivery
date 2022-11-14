package com.vald3nir.sales.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.core.presentation.BaseFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.extensions.setupLayoutManager
import com.vald3nir.core_repository.baseDiffUtil
import com.vald3nir.sales.databinding.FragmentOrderDetailBinding
import com.vald3nir.sales.databinding.ItemOrderDetailBinding
import com.vald3nir.core_utils.extensions.format
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : BaseFragment() {

    private val viewModel: MyOrdersViewModel by viewModel()
    lateinit var binding: FragmentOrderDetailBinding
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
        orderItemDTO: com.vald3nir.core.repository.dtos.OrderItemDTO
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
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            initViews()
            setupObservers()
        }
        viewModel.loadOrder()
    }

    private fun FragmentOrderDetailBinding.initViews() {
        clcOrderDetail.apply {
            setTab("Itens do Pedido")
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
    }

    private fun FragmentOrderDetailBinding.setupObservers() {
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
            }
        }
    }
}