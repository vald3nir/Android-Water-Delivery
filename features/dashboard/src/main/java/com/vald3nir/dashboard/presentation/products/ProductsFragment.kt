package com.vald3nir.dashboard.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.core.presentation.BaseFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.components.CustomSheetDialog
import com.vald3nir.core_ui.extensions.setupLayoutManager
import com.vald3nir.dashboard.R
import com.vald3nir.dashboard.databinding.FragmentProductsBinding
import com.vald3nir.dashboard.databinding.ProductItemViewBinding
import com.vald3nir.core_repository.baseDiffUtil
import com.vald3nir.core_utils.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : BaseFragment() {

    private val viewModel: ProductViewModel by viewModel()
    private lateinit var binding: FragmentProductsBinding

    private val mainCardAdapter = CustomDifferAdapter(
        bindingInflater = ProductItemViewBinding::inflate,
        list = listOf(),
        itemDiffUtil = baseDiffUtil(),
        onBind = { productDTO, itemViewBinding, _, _ ->
            bindAdapter(itemViewBinding, productDTO)
        }
    )

    private fun bindAdapter(
        itemViewBinding: ProductItemViewBinding,
        productDTO: com.vald3nir.core.repository.dtos.ProductDTO
    ) {
        itemViewBinding.apply {
            txvName.text = productDTO.name
            txvPrice.text = productDTO.price.toMoney()
            imvPhoto.loadImageBase64(productDTO.imageBase64, R.drawable.generic_water)
            txvDelete.setOnClickListener { showDeleteProductDialog(productDTO) }
        }
    }

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
        binding.setupObservers()
        viewModel.resetCategories()
        viewModel.loadProducts()
    }

    private fun FragmentProductsBinding.initViews() {
        clcListProducts.apply {
            setTabs(viewModel.productCategories)
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
    }

    private fun FragmentProductsBinding.setupObservers() {
        mainCardAdapter.setOnItemClickListener(listener = { item, position ->
            viewModel.saveInMemory(item)
            navigationToFragment(R.id.action_navigation_products_to_product_detail)
        })

        viewModel.products.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it)
            clcListProducts.notifyListSize(it.size)
        }

        fabAddProduct.setOnClickListener {
            navigationToFragment(R.id.action_navigation_products_to_product_detail)
        }
    }


    private fun showDeleteProductDialog(productDTO: com.vald3nir.core.repository.dtos.ProductDTO) {
        val items: List<CustomSheetDialog.CustomItemSheet> = listOf(
            CustomSheetDialog.CustomItemSheet(
                icon = R.drawable.ic_delete,
                title = "Sim",
                titleCor = R.color.red,
                action = { viewModel.deleteProduct(productDTO) }
            ),
            CustomSheetDialog.CustomItemSheet(
                icon = R.drawable.ic_cancel,
                title = "Não",
                titleCor = R.color.blue,
                action = { dialog?.dismiss() }
            ),
        )
        dialog = CustomSheetDialog(
            context = requireContext(),
            items = items,
            title = "Deseja remover o produto: ${productDTO.name}?",
            titleColor = R.color.blue
        )
        dialog?.show()
    }
}


