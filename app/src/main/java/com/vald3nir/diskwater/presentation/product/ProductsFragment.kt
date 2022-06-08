package com.vald3nir.diskwater.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.databinding.FragmentProductsBinding
import com.vald3nir.diskwater.databinding.ProductItemViewBinding
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.diskwater.domain.utils.toMutableBaseList
import com.vald3nir.toolkit.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.componets.lists.CustomListComponent
import com.vald3nir.toolkit.data.dto.BaseDTO
import com.vald3nir.toolkit.data.dto.baseDiffUtil
import com.vald3nir.toolkit.extensions.setupLayoutManager
import com.vald3nir.toolkit.extensions.setupToolbar
import com.vald3nir.toolkit.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : BaseFragment() {

    private val viewModel: ProductViewModel by viewModel()
    lateinit var binding: FragmentProductsBinding

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

                txtName.text = baseDTO.name
                txtPrice.text = baseDTO.price.toMoney()

                imvPhoto.apply {
                    viewModel.loadProductImage(
                        onSuccess = { loadImage(it, R.drawable.generic_water) },
                        onError = { loadImage(R.drawable.generic_water) }
                    )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        viewModel.appView = appView
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                title = getString(R.string.my_products),
                showBackButton = true,
            )
            clcOrdersOpen.apply {
                setTab(
                    CustomListComponent.CustomListTab(
                        title = getString(R.string.mineral_waters)
                    )
                )
                getRecyclerView().apply {
                    adapter = mainCardAdapter
                    setupLayoutManager()
                }
            }
            btnAddProducts.apply {
                setTitle(getString(R.string.add_product))
                setTitleColor(R.color.white)
                setBackgroundDrawable(R.drawable.button_white_layout)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        loadProducts()
    }

    private fun loadProducts() {
        binding.btnAddProducts.showLoading(true)
        viewModel.loadProducts()
    }

    private fun setupObservers() {

        binding.btnAddProducts.setOnClickListener {
            viewModel.replaceFragment(FragmentEnum.PRODUCT_DETAIL)
        }

        mainCardAdapter.setOnItemClickListener(listener = { item, pos ->
            viewModel.replaceFragment(FragmentEnum.PRODUCT_DETAIL, item)
        })

        viewModel.products.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it.toMutableBaseList())
            binding.clcOrdersOpen.notifyListSize(it.size)
            binding.btnAddProducts.showLoading(false)
        }
    }
}