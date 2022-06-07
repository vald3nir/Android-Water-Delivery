package com.vald3nir.diskwater.presentation.product

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.common.BaseViewModel
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.domain.use_cases.product.ProductUseCase
import com.vald3nir.toolkit.componets.lists.CustomListComponent
import com.vald3nir.toolkit.extensions.toBase64
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productUseCase: ProductUseCase,
) : BaseViewModel() {

    private val _products = MutableLiveData<MutableList<ProductDTO>>()
    val products: LiveData<MutableList<ProductDTO>> = _products
    var productDTO: ProductDTO? = null
    var flagCreateProduct: Boolean = false

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
        productDTO = baseFragment.loadExtraDTO() as ProductDTO
        if (productDTO == null) {
            productDTO = ProductDTO()
            flagCreateProduct = true
        } else {
            flagCreateProduct = false
        }
    }

    fun insertNewProduct(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit) {
        viewModelScope.launch {
            productUseCase.insertNewProduct(productDTO, onSuccess, onError)
        }
    }


    fun updateProductImage(bitmap: Bitmap, onSuccess: () -> Unit) {
        viewModelScope.launch {
            productDTO?.imageBase64 = bitmap.toBase64()
            onSuccess.invoke()
        }
    }

    fun loadProductImage(onSuccess: (uri: Uri) -> Unit, onError: (e: Exception?) -> Unit) {
//        if (productDTO?.name?.isNotBlank() == true) {
//            viewModelScope.launch {
//                productUseCase.loadProductImage(
//                    productName = productDTO?.name.orEmpty(),
//                    onSuccess,
//                    onError
//                )
//            }
//        }
    }

    val tabsList =
        listOf(
            CustomListComponent.CustomListTab(
                title = "Águas Minerais",
                onTabSelectedListener = { })
        )
}