package com.vald3nir.diskwater.presentation.features.sales.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.app.base.BaseViewModel
import com.vald3nir.diskwater.domain.dtos.OrderDTO
import com.vald3nir.diskwater.domain.dtos.PaymentType
import com.vald3nir.diskwater.domain.dtos.ProductDTO
import com.vald3nir.diskwater.domain.usecases.OrderUseCase
import com.vald3nir.diskwater.domain.usecases.ProductUseCase
import kotlinx.coroutines.launch

class CreateOrderViewModel(
    private val orderUseCase: OrderUseCase,
    private val productUseCase: ProductUseCase,
) : BaseViewModel() {

    private val _order = MutableLiveData<OrderDTO>()
    val order: LiveData<OrderDTO> = _order

    private val _products = MutableLiveData<MutableList<ProductDTO>>()
    val products: LiveData<MutableList<ProductDTO>> = _products

    private var productCategorySelected = listProductCategories()[0]
    private fun listProductCategories() = productUseCase.listProductCategories()

    val productCategories = productUseCase.listProductCategoriesTab() {
        productCategorySelected = it
        loadProducts()
    }

    fun loadCurrentOrder() {
        _order.postValue(orderUseCase.loadCurrentOrder())
    }

    fun registerItem(productDTO: ProductDTO, quantity: Int) {
        orderUseCase.registerItem(productDTO, quantity)
        loadCurrentOrder()
    }

    fun getQuantity(productDTO: ProductDTO): String? {
        return orderUseCase.getItemQuantity(productDTO)
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

    fun addPaymentType(paymentType: PaymentType) {
        orderUseCase.addPaymentType(paymentType)
    }

    fun requestOrder(onSuccess: () -> Unit, onError: (e: Exception?) -> Unit) {
        viewModelScope.launch {
            orderUseCase.requestOrder(onSuccess, onError)
        }
    }
}