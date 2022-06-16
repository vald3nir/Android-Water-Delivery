package com.vald3nir.diskwater.presentation.product

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.common.BaseViewModel
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.data.form.DataUserInputForm
import com.vald3nir.diskwater.data.form.ProductInputForm
import com.vald3nir.diskwater.databinding.ActivityLoginBinding
import com.vald3nir.diskwater.domain.use_cases.product.ProductUseCase
import com.vald3nir.toolkit.extensions.toBase64
import com.vald3nir.toolkit.validations.isEmailValid
import com.vald3nir.toolkit.validations.isPasswordValid
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productUseCase: ProductUseCase,
) : BaseViewModel() {

    private var productFormIsValid = true
    private val _productForm = MutableLiveData<ProductInputForm>()
    val productForm: LiveData<ProductInputForm> = _productForm

    private val _products = MutableLiveData<MutableList<ProductDTO>>()
    val products: LiveData<MutableList<ProductDTO>> = _products

    private val _product = MutableLiveData<ProductDTO>()
    val product: LiveData<ProductDTO> = _product

    fun loadProducts() {
        viewModelScope.launch {
            productUseCase.listProducts(onSuccess = {
                _products.postValue(it)
            }, onError = {
                _products.postValue(mutableListOf())
            })
        }
    }

    fun loadData(baseFragment: BaseFragment) {
        var productDTO = baseFragment.loadExtraDTO() as ProductDTO?
        if (productDTO == null) {
            productDTO = ProductDTO()
        } else {
            productDTO.isNew = false
        }
        productDTO.let { _product.postValue(it) }
    }

    fun updateProduct(
        category: String,
        name: String,
        price: Float,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        if (productFormIsValid) {
            viewModelScope.launch {
                _product.value.apply {
                    this?.isNew = false
                    this?.category = category
                    this?.name = name
                    this?.price = price
                    productUseCase.updateProduct(this, onSuccess, onError)
                }
            }
        } else {
            onError.invoke(null)
        }
    }

    fun updateProductImage(bitmap: Bitmap, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _product.value?.imageBase64 = bitmap.toBase64()
            onSuccess.invoke()
        }
    }

    fun deleteProduct(productDTO: ProductDTO) {
        viewModelScope.launch {
            productUseCase.deleteProduct(
                productDTO,
                onSuccess = { loadProducts() },
                onError = { showError(it) }
            )
        }
    }

    fun checkProductData(
        name: String,
        price: Float,
    ) {
        productFormIsValid = true
        val inputForm = ProductInputForm()

        if (name.isBlank()) {
            productFormIsValid = false
            inputForm.nameError = "O nome não pode ser vazio"
        }

        if (price <= 0) {
            productFormIsValid = false
            inputForm.priceError = "o preço deve ser maior que zero"
        }

        if (!productFormIsValid) {
            _productForm.value = inputForm
        }
    }
}