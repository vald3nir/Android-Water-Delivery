package com.vald3nir.diskwater.presentation.product

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.databinding.FragmentProductDetailBinding
import com.vald3nir.toolkit.extensions.afterTextChanged
import com.vald3nir.toolkit.extensions.setupToolbar
import com.vald3nir.toolkit.extensions.toFloatValue
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
        setupObservers()
    }

    private fun initViews() {
        viewModel.loadData(this)
        viewModel.appView = appView
        binding.apply {

            toolbar.setupToolbar(
                activity = activity,
                showBackButton = true,
                title = getString(
                    if (viewModel.flagCreateProduct) R.string.add_product else R.string.update_product
                )
            )

            imvPhoto.apply {
                viewModel.loadProductImage(
                    onSuccess = { loadImage(it, R.drawable.generic_water) },
                    onError = { loadImage(R.drawable.generic_water) }
                )
            }

            txtChangeImage.setOnClickListener { takePhoto() }
            edtName.setText(viewModel.productDTO?.name)
            edtPrice.setText(viewModel.productDTO?.price.toString())

            btnSaveProducts.apply {
                setTitle(getString(if (viewModel.productDTO == null) R.string.register else R.string.update))
                setTitleColor(R.color.white)
                setBackgroundDrawable(R.drawable.button_white_layout)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        binding.apply {
            imvPhoto.setOnClickListener { takePhoto() }
            btnSaveProducts.setOnClickListener { insertNewProduct() }
            viewModel.productDTO.apply {
                edtName.afterTextChanged(afterTextChanged = { this?.name = it })
                edtPrice.afterTextChanged(afterTextChanged = { this?.price = it.toFloatValue() })
            }
        }
    }

    private fun FragmentProductDetailBinding.insertNewProduct() {
        btnSaveProducts.showLoading(true)
        viewModel.insertNewProduct(
            onSuccess = {
                btnSaveProducts.showLoading(false)
                activity?.onBackPressed()
            },
            onError = {
                btnSaveProducts.showLoading(false)
                showMessage(it)
            }
        )
    }

    private fun updateProductImage(bitmap: Bitmap) {
        binding.apply {
            imvPhoto.setImageBitmap(bitmap)
            btnSaveProducts.showLoading(true)
            viewModel.updateProductImage(bitmap, onSuccess = {
                btnSaveProducts.showLoading(false)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            updateProductImage(data?.extras?.get("data") as Bitmap)
        }
    }
}