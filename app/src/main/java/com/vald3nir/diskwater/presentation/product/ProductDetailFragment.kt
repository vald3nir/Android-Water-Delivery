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

            imvPhoto.apply {
                viewModel.loadProductImage(
                    onSuccess = { loadImage(it, R.drawable.generic_water) },
                    onError = { loadImage(R.drawable.generic_water) }
                )
            }

            btnSaveProducts.apply {
                setTitleColor(R.color.white)
                setBackgroundDrawable(R.drawable.button_white_layout)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setupObservers() {

        binding.apply {
            imvPhoto.setOnClickListener { takePhoto() }
            txtChangeImage.setOnClickListener { takePhoto() }
            btnSaveProducts.setOnClickListener { updateProduct() }
        }

        viewModel.product.observe(viewLifecycleOwner) {
            binding.apply {
                edtName.setText(it.name)
                edtPrice.setText(it.price.toString())
                btnSaveProducts.setTitle(getString(if (it.isNew) R.string.register else R.string.update))
                toolbar.setupToolbar(
                    activity = activity,
                    showBackButton = true,
                    title = getString(
                        if (it.isNew) R.string.add_product else R.string.update_product
                    )
                )
            }
        }
    }

    private fun FragmentProductDetailBinding.updateProduct() {
        btnSaveProducts.showLoading(true)
        viewModel.updateProduct(
            name = edtName.text.toString(),
            price = edtPrice.text.toString().toFloatValue(),
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