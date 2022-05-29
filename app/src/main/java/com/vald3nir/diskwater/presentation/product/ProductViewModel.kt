package com.vald3nir.diskwater.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vald3nir.diskwater.common.BaseViewModel
import com.vald3nir.diskwater.data.dto.ProductDTO

class ProductViewModel() : BaseViewModel() {

    private val _products = MutableLiveData<MutableList<ProductDTO>>()
    val products: LiveData<MutableList<ProductDTO>> = _products

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
}