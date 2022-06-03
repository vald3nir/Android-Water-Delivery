package com.vald3nir.diskwater.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.databinding.FragmentProductDetailBinding
import com.vald3nir.diskwater.databinding.FragmentProductsBinding
import com.vald3nir.toolkit.extensions.setupToolbar
import com.vald3nir.toolkit.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : BaseFragment() {

    private val viewModel: ProductViewModel by viewModel()
    lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewModel.loadData(this)
        viewModel.appView = appView
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                showBackButton = true,
                title = getString(
                    if (viewModel.productDTO == null) R.string.add_product else R.string.update_product
                )
            )
            edtName.setText(viewModel.productDTO?.name)
            edtPrice.setText(viewModel.productDTO?.price.toMoney())
            btnSaveProducts.apply {
                text = getString(
                    if (viewModel.productDTO == null) R.string.register else R.string.update
                )
                setOnClickListener { viewModel.clickSaveButton() }
            }
        }
    }
}