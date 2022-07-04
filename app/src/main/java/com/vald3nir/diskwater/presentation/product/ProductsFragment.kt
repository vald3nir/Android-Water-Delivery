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
import com.vald3nir.toolkit.core.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.core.componets.customviews.CustomSheetDialog
import com.vald3nir.toolkit.data.dto.baseDiffUtil
import com.vald3nir.toolkit.utils.extensions.setupLayoutManager
import com.vald3nir.toolkit.utils.extensions.setupToolbar
import com.vald3nir.toolkit.utils.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : BaseFragment() {

    private val viewModel: ProductViewModel by viewModel()
    lateinit var binding: FragmentProductsBinding

    private val mainCardAdapter = CustomListAdapterDiffer(
        bindingInflater = ProductItemViewBinding::inflate,
        list = listOf(),
        itemDiffUtil = baseDiffUtil(),
        onBind = { productDTO, itemViewBinding, _, _ ->
            bindAdapter(itemViewBinding, productDTO)
        }
    )

    private fun bindAdapter(
        itemViewBinding: ProductItemViewBinding,
        productDTO: ProductDTO
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupObservers()
        binding.btnAddProducts.showLoading(true)
        viewModel.resetCategories()
        viewModel.loadProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        binding.initViews()
        return binding.root
    }

    private fun FragmentProductsBinding.initViews() {
        toolbar.setupToolbar(
            activity = activity,
            title = getString(R.string.my_products),
            showBackButton = true,
        )
        clcOrdersOpen.apply {
            setTabs(viewModel.productCategories)
            getRecyclerView().apply {
                adapter = mainCardAdapter
                setupLayoutManager()
            }
        }
        btnAddProducts.setButtonTitle(getString(R.string.add_product))
    }

    private fun FragmentProductsBinding.setupObservers() {

        btnAddProducts.setButtonClickListener {
            viewModel.replaceFragment(FragmentEnum.PRODUCT_DETAIL)
        }

        mainCardAdapter.setOnItemClickListener(listener = { item, position ->
            viewModel.replaceFragment(FragmentEnum.PRODUCT_DETAIL, item)
        })

        viewModel.products.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it)
            clcOrdersOpen.notifyListSize(it.size)
            btnAddProducts.showLoading(false)
        }
    }

    private fun showDeleteProductDialog(productDTO: ProductDTO) {
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