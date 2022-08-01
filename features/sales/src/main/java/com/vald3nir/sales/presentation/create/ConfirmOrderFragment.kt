package com.vald3nir.sales.presentation.create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.vald3nir.commom.domain.dtos.OrderItemDTO
import com.vald3nir.commom.domain.dtos.PaymentType
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.extensions.setupLayoutManager
import com.vald3nir.repository.baseDiffUtil
import com.vald3nir.sales.R
import com.vald3nir.sales.databinding.FragmentConfirmOrderBinding
import com.vald3nir.sales.databinding.ItemOrderDetailBinding
import com.vald3nir.utils.extensions.format
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmOrderFragment : BaseFragment() {

    private val viewModel: CreateOrderViewModel by viewModel()
    private lateinit var binding: FragmentConfirmOrderBinding

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
        binding = FragmentConfirmOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
        binding.setupObservers()
        viewModel.loadCurrentOrder()
    }

    private fun FragmentConfirmOrderBinding.initViews() {
        clcOrderDetail.apply {
            setTab("Itens do Pedido")
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
        btnNext.apply {
            setup(
                title = R.string.finish,
                clickListener = {
                    requestOrder()
                }
            )
            showLoading(true)
        }
    }

    private fun requestOrder() {
        viewModel.requestOrder(onSuccess = { activity?.finish() }, onError)
    }

    private fun FragmentConfirmOrderBinding.selectCurrentPaymentsType(type: PaymentType) {
        when (type) {
            PaymentType.MONEY -> {
                rbMoney.isChecked = true
            }
            PaymentType.PIX -> {
                rbPix.isChecked = true
            }
            PaymentType.CARD -> {
                rbCard.isChecked = true
            }
        }
    }

    private fun FragmentConfirmOrderBinding.setupObservers() {

        rbPaymentsTypes.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_money -> {
                    viewModel.addPaymentType(PaymentType.MONEY)
                }
                R.id.rb_pix -> {
                    viewModel.addPaymentType(PaymentType.PIX)
                }
                R.id.rb_card -> {
                    viewModel.addPaymentType(PaymentType.CARD)
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
                    btnNext.isVisible = value > 0
                }
                items.let {
                    mainCardAdapter.submitList(it.toMutableList())
                    clcOrderDetail.notifyListSize(it.size)
                    btnNext.showLoading(false)
                }
                selectCurrentPaymentsType(order.paymentType)
            }
        }
    }
}