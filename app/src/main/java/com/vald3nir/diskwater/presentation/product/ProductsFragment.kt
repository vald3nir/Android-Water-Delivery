package com.vald3nir.diskwater.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.databinding.FragmentProductDetailBinding
import com.vald3nir.diskwater.databinding.ProductItemViewBinding
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.diskwater.domain.utils.toMutableBaseList
import com.vald3nir.toolkit.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.data.BaseDTO
import com.vald3nir.toolkit.data.baseDiffUtil
import com.vald3nir.toolkit.extensions.setupLayoutManager
import com.vald3nir.toolkit.extensions.setupToolbar
import com.vald3nir.toolkit.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : BaseFragment() {

    private val viewModel: ProductViewModel by viewModel()
    lateinit var binding: FragmentProductDetailBinding

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
        procuct: BaseDTO,
        itemViewBinding: ProductItemViewBinding,
        position: Int,
        any: Any
    ) {
        if (procuct is ProductDTO)
            itemViewBinding.apply {
                txtName.text = procuct.name
                txtPrice.text = procuct.price.toMoney()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        viewModel.appView = appView
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                title = "Meus Produtos",
                showBackButton = true,
            )
            clcOrdersOpen.apply {
                setTabs(viewModel.tabsList)
                getRecyclerView().apply {
                    adapter = mainCardAdapter
                    setupLayoutManager()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadProducts()
    }

    private fun setupObservers() {

        binding.btnSaveProducts.setOnClickListener {
        }

        mainCardAdapter.setOnItemClickListener(listener = { item, pos ->
            viewModel.replaceFragment(FragmentEnum.PRODUCT_DETAIL,  item)
        })

        viewModel.products.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it.toMutableBaseList())
            binding.clcOrdersOpen.notifyListSize(it.size)
        }
    }
}