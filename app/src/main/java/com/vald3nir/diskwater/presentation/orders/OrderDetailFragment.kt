package com.vald3nir.diskwater.presentation.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.OrderItemDTO
import com.vald3nir.diskwater.data.dto.PaymentType
import com.vald3nir.diskwater.databinding.FragmentOrderDetailBinding
import com.vald3nir.diskwater.databinding.ItemOrderDetailBinding
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.toolkit.core.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.data.dto.baseDiffUtil
import com.vald3nir.toolkit.utils.extensions.format
import com.vald3nir.toolkit.utils.extensions.setupLayoutManager
import com.vald3nir.toolkit.utils.extensions.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentOrderDetailBinding

    private val mainCardAdapter = CustomListAdapterDiffer(
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
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
        binding.setupObservers()
        viewModel.loadAddress()
        viewModel.loadOrderDetail()
    }

    private fun FragmentOrderDetailBinding.initViews() {
        toolbar.setupToolbar(
            activity = activity,
            title = "Detalhe do Pedido"
        )
        clcOrderDetail.apply {
            setTab("Itens do Pedido")
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
        btnNext.apply {
            setup(
                title = R.string.next,
                clickListener = {
                    requestOrder()
                }
            )
            showLoading(true)
        }
    }

    private fun requestOrder() {
        viewModel.replaceFragment(FragmentEnum.ORDER_SUCCESS)
    }

    private fun FragmentOrderDetailBinding.setupObservers() {
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

        cardAddress.setOnClickListener {
            viewModel.replaceFragment(FragmentEnum.CONFIRM_ADDRESS)
        }

        viewModel.shoppingCartTotal.observe(viewLifecycleOwner) { value ->
            txvTotalValue.text = value.format()
            btnNext.isVisible = value > 0
        }

        viewModel.addressFields.observe(viewLifecycleOwner) {
            txvAddressDetail.text = it.toString()
        }

        viewModel.itemsOrder.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it)
            clcOrderDetail.notifyListSize(it.size)
            btnNext.showLoading(false)
        }
    }
}

