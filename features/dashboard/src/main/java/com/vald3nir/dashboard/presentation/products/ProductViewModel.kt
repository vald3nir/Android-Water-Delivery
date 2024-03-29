package com.vald3nir.dashboard.presentation.products

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.core.presentation.BaseViewModel
import com.vald3nir.dashboard.domain.ProductUseCase
import com.vald3nir.dashboard.domain.forms.ProductInputForm
import com.vald3nir.core_utils.extensions.toBase64
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productUseCase: ProductUseCase,
) : BaseViewModel() {

    private var productFormIsValid = true
    private val _productForm = MutableLiveData<ProductInputForm>()
    val productForm: LiveData<ProductInputForm> = _productForm

    private val _products = MutableLiveData<MutableList<com.vald3nir.core.repository.dtos.ProductDTO>>()
    val products: LiveData<MutableList<com.vald3nir.core.repository.dtos.ProductDTO>> = _products

    private val _product = MutableLiveData<com.vald3nir.core.repository.dtos.ProductDTO>()
    val product: LiveData<com.vald3nir.core.repository.dtos.ProductDTO> = _product

    private var productCategorySelected = listProductCategories()[0]
    fun listProductCategories() = productUseCase.listProductCategories()

    val productCategories = productUseCase.listProductCategoriesTab() {
        productCategorySelected = it
        loadProducts()
    }

    fun resetCategories() {
        productCategorySelected = listProductCategories()[0]
    }

    fun loadProducts() {
        viewModelScope.launch {
            productUseCase.listProducts(
                category = productCategorySelected,
                onSuccess = {
                    _products.postValue(it)
                }, onError = {
                    _products.postValue(mutableListOf())
                }
            )
        }
    }

    fun loadData() {
        var productDTO = productUseCase.loadInMemory()
        if (productDTO == null) {
            productDTO = com.vald3nir.core.repository.dtos.ProductDTO()
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

    fun deleteProduct(productDTO: com.vald3nir.core.repository.dtos.ProductDTO) {
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

    fun saveInMemory(item: com.vald3nir.core.repository.dtos.ProductDTO?) {
        productUseCase.saveInMemory(item)
    }
}