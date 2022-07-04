package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.databinding.FragmentShoppingCartBinding
import com.vald3nir.diskwater.databinding.ItemShoppingCartBinding
import com.vald3nir.toolkit.core.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.data.dto.baseDiffUtil
import com.vald3nir.toolkit.utils.createNumbersArray
import com.vald3nir.toolkit.utils.extensions.format
import com.vald3nir.toolkit.utils.extensions.setupLayoutManager
import com.vald3nir.toolkit.utils.extensions.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingCartFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentShoppingCartBinding

    private val mainCardAdapter = CustomListAdapterDiffer(
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
            cspQuantity.setup(
                list = createNumbersArray(start = 0, end = 10),
                textLayout = R.layout.item_quantity_shopping_cart,
                textColorItemSelected = R.color.black,
                onItemSelected = {

                }
            )
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
        toolbar.setupToolbar(
            activity = activity,
            title = getString(R.string.items_order)
        )
        btnNext.apply {
            setButtonTitle(R.string.next)
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
        viewModel.products.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it)
            clcShopping.notifyListSize(it.size)
            btnNext.showLoading(false)
        }
    }
}