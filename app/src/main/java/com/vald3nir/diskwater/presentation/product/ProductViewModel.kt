package com.vald3nir.diskwater.presentation.product

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.common.BaseViewModel
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.domain.use_cases.product.ProductUseCase
import com.vald3nir.toolkit.componets.lists.CustomListComponent
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productUseCase: ProductUseCase,
) : BaseViewModel() {

    private val _products = MutableLiveData<MutableList<ProductDTO>>()
    val products: LiveData<MutableList<ProductDTO>> = _products
    var productDTO: ProductDTO? = null

    fun loadProducts() {
        _products.postValue(
            mutableListOf(
                ProductDTO(
                    name = "Renágua 20L",
                    price = 5.20f
                ),
                ProductDTO(
                    name = "Clareza 20L",
                    price = 5.50f
                ),
                ProductDTO(
                    name = "Indaiá 20L",
                    price = 11.50f
                ),
                ProductDTO(
                    name = "Naturágua 20L",
                    price = 11.90f
                ),
                ProductDTO(
                    name = "Neblina 20L",
                    price = 11.90f
                )
            )
        )
    }

    fun loadData(baseFragment: BaseFragment) {
        this.productDTO = baseFragment.loadExtraDTO() as ProductDTO
    }

    fun clickSaveButton() {

    }

    fun uploadProductImage(bitmap: Bitmap, productName: String, onSuccess: () -> Unit) {
        if (productName.isBlank()) {
            showMessage(getString(R.string.unnamed_product))
        } else {
            viewModelScope.launch {
                productUseCase.uploadProductImage(bitmap, productName, onSuccess, onError = {
                    showError(it)
                })
            }
        }
    }

    val tabsList =
        listOf(
            CustomListComponent.CustomListTab(
                title = "Águas Minerais",
                onTabSelectedListener = { })
        )
}