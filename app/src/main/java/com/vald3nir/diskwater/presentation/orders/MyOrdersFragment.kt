package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.databinding.FragmentMyOrdersBinding
import com.vald3nir.diskwater.databinding.ProductItemViewBinding
import com.vald3nir.toolkit.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.data.dto.BaseDTO
import com.vald3nir.toolkit.data.dto.baseDiffUtil
import com.vald3nir.toolkit.extensions.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOrdersFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentMyOrdersBinding
    private val mainCardAdapter by lazy {
        val adapter = CustomListAdapterDiffer(
            bindingInflater = ProductItemViewBinding::inflate,
            list = listOf(),
            itemDiffUtil = baseDiffUtil(),
            ::bindAdapter
        )
        adapter
    }

    private fun bindAdapter(
        baseDTO: BaseDTO,
        itemViewBinding: ProductItemViewBinding,
        position: Int,
        any: Any
    ) {
        if (baseDTO is ProductDTO) {
            itemViewBinding.apply {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        viewModel.appView = appView
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                title = getString(R.string.my_last_orders),
                showBackButton = false,
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        loadLastOrders()
    }

    private fun loadLastOrders() {
        binding.btnNewOrder.showLoading(true)
        viewModel.loadLastOrders()
    }

    private fun setupObservers() {

//        binding.btnAddProducts.setOnClickListener {
//            viewModel.replaceFragment(FragmentEnum.PRODUCT_DETAIL)
//        }
//
//        mainCardAdapter.setOnItemClickListener(listener = { item, pos ->
//            viewModel.replaceFragment(FragmentEnum.PRODUCT_DETAIL, item)
//        })
//
//        viewModel.products.observe(viewLifecycleOwner) {
//            mainCardAdapter.submitList(it.toMutableBaseList())
//            binding.clcOrdersOpen.notifyListSize(it.size)
//            binding.btnAddProducts.showLoading(false)
//        }
    }
}