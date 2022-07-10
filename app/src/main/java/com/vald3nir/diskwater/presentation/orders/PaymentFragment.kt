package com.vald3nir.diskwater.presentation.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.PaymentType
import com.vald3nir.diskwater.databinding.FragmentPaymentBinding
import com.vald3nir.diskwater.databinding.ItemPaymentBinding
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.toolkit.core.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.data.dto.baseDiffUtil
import com.vald3nir.toolkit.utils.extensions.setupLayoutManager
import com.vald3nir.toolkit.utils.extensions.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentPaymentBinding

    private val mainCardAdapter = CustomListAdapterDiffer(
        bindingInflater = ItemPaymentBinding::inflate,
        list = listOf(),
        itemDiffUtil = baseDiffUtil(),
        onBind = { paymentType, itemViewBinding, _, _ ->
            bindAdapter(itemViewBinding, paymentType)
        }
    )

    @SuppressLint("SetTextI18n")
    private fun bindAdapter(
        itemViewBinding: ItemPaymentBinding,
        paymentType: PaymentType
    ) {
        itemViewBinding.apply {
            txvTitle.text = paymentType.title
            root.setOnClickListener {
                viewModel.registerPaymentType(paymentType)
                viewModel.replaceFragment(FragmentEnum.ORDER_SUCCESS)
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
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
    }

    private fun FragmentPaymentBinding.initViews() {
        toolbar.setupToolbar(
            activity = activity,
            title = "Forma de Pagamento"
        )
        clcPaymentsType.apply {
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
            val it = viewModel.loadPaymentType()
            mainCardAdapter.submitList(it)
            notifyListSize(it.size)
        }
    }

}