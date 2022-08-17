package com.vald3nir.sales.presentation.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.vald3nir.commom.domain.dtos.ProductDTO
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.extensions.setupLayoutManager
import com.vald3nir.core_repository.baseDiffUtil
import com.vald3nir.sales.R
import com.vald3nir.sales.databinding.FragmentShoppingCartBinding
import com.vald3nir.sales.databinding.ItemShoppingCartBinding
import com.vald3nir.core_utils.createNumbersArray
import com.vald3nir.core_utils.extensions.format
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingCartFragment : BaseFragment() {

    private val viewModel: CreateOrderViewModel by viewModel()
    private lateinit var binding: FragmentShoppingCartBinding

    private val mainCardAdapter = CustomDifferAdapter(
        bindingInflater = ItemShoppingCartBinding::inflate,
        list = listOf(),
        itemDiffUtil = baseDiffUtil(),
        onBind = { productDTO, itemViewBinding, _, _ ->
            bindAdapter(itemViewBinding, productDTO)
        }
    )

    private fun bindAdapter(
        itemViewBinding: ItemShoppingCartBinding,
        productDTO: ProductDTO
    ) {
        itemViewBinding.apply {
            txvTitle.text = productDTO.name
            txvPrice.text = productDTO.price.format()
            imvPhoto.loadImageBase64(productDTO.imageBase64, R.drawable.generic_water)
            cspQuantity.apply {
                setup(
                    list = createNumbersArray(start = 0, end = 10),
                    textLayout = R.layout.item_quantity_shopping_cart,
                    textColorItemSelected = R.color.black,
                    onItemSelected = { size ->
                        viewModel.registerItem(productDTO, size.toInt())
                    }
                )
                setItemSelection(viewModel.getQuantity(productDTO))
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
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
        binding.setupObservers()
        viewModel.resetCategories()
        viewModel.loadProducts()
    }

    private fun FragmentShoppingCartBinding.initViews() {
        btnNext.apply {
            setup(
                title = R.string.next,
                clickListener = {
                    navigationToFragment(R.id.action_navigation_shopping_to_navigation_confirm_address)
                }
            )
            showLoading(true)
        }
        clcShopping.apply {
            setTabs(viewModel.productCategories)
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
    }

    private fun FragmentShoppingCartBinding.setupObservers() {
        viewModel.order.observe(viewLifecycleOwner) { order ->
            order.total.let { value ->
                txvTotalValue.text = value.format()
                btnNext.isVisible = value > 0
            }
        }
        viewModel.products.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it)
            clcShopping.notifyListSize(it.size)
            btnNext.showLoading(false)
        }
    }
}