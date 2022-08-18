package com.vald3nir.dashboard.presentation.products

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.Nullable
import com.vald3nir.base_ui.view.BaseFragment
import com.vald3nir.core_ui.extensions.afterTextChanged
import com.vald3nir.dashboard.R
import com.vald3nir.dashboard.databinding.FragmentProductDetailBinding
import com.vald3nir.core_utils.extensions.toFloatValue
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : BaseFragment() {

    private val viewModel: ProductViewModel by viewModel()
    lateinit var binding: FragmentProductDetailBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigationToFragment(R.id.action_navigation_product_detail_to_navigation_products)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupObservers()
        viewModel.loadData()
    }

    @SuppressLint("SetTextI18n")
    private fun FragmentProductDetailBinding.setupObservers() {

        imvPhoto.setOnClickListener { takePhoto() }
        txtChangeImage.setOnClickListener { takePhoto() }
        edtName.afterTextChanged { onDataChange() }
        edtPrice.afterTextChanged { onDataChange() }
        spProductCategory.setup(
            textColorItemSelected = R.color.white,
            list = viewModel.listProductCategories(),
        )

        viewModel.product.observe(viewLifecycleOwner) {
            setToolbarTitle(if (it.isNew) R.string.add_product else R.string.update_product)
            edtName.setText(it.name)
            edtPrice.setText(it.price.toString())
            imvPhoto.loadImageBase64(it.imageBase64, R.drawable.generic_water)
            spProductCategory.setItemSelection(item = it.category)
            btnSaveProducts.setup(
                title = if (it.isNew) R.string.register else R.string.update,
                clickListener = { updateProduct() }
            )
        }

        viewModel.productForm.observe(viewLifecycleOwner) {
            edtNameLayout.error = it.nameError
            edtPriceLayout.error = it.priceError
            btnSaveProducts.showLoading(false)
        }
    }

    private fun FragmentProductDetailBinding.clearError() {
        edtNameLayout.error = null
        edtPriceLayout.error = null
    }

    private fun FragmentProductDetailBinding.onDataChange() {
        clearError()
        viewModel.checkProductData(
            name = edtName.text.toString(),
            price = edtPrice.text.toString().toFloatValue()
        )
    }

    private fun FragmentProductDetailBinding.updateProduct() {
        btnSaveProducts.showLoading(true)
        viewModel.updateProduct(
            category = spProductCategory.itemSelected,
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            updateProductImage(data?.extras?.get("data") as Bitmap)
        }
    }

    override fun onDestroy() {
        viewModel.saveInMemory(null)
        super.onDestroy()
    }
}