package com.vald3nir.diskwater.presentation.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.common.BaseViewModel
import com.vald3nir.diskwater.data.dto.AddressDTO
import com.vald3nir.diskwater.data.dto.OrderDTO
import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.diskwater.data.form.AddressInputForm
import com.vald3nir.diskwater.domain.use_cases.address.AddressUseCase
import com.vald3nir.diskwater.domain.use_cases.product.ProductUseCase
import kotlinx.coroutines.launch

class OrderViewModel(
    private val addressUseCase: AddressUseCase,
    private val productUseCase: ProductUseCase,
) : BaseViewModel() {

    private val _myOrders = MutableLiveData<List<OrderDTO>>()
    val myOrders: LiveData<List<OrderDTO>> = _myOrders

    private val _order = MutableLiveData<OrderDTO>()
    val order: LiveData<OrderDTO> = _order

    private val _addressInputForm = MutableLiveData<AddressInputForm>()
    val addressInputForm: LiveData<AddressInputForm> = _addressInputForm

    private val _addressFields = MutableLiveData<AddressDTO>()
    val addressFields: LiveData<AddressDTO> = _addressFields

    private val _products = MutableLiveData<MutableList<ProductDTO>>()
    val products: LiveData<MutableList<ProductDTO>> = _products

    private var productCategorySelected = listProductCategories()[0]
    fun listProductCategories() = productUseCase.listProductCategories()

    val productCategories = productUseCase.listProductCategoriesTab() {
        productCategorySelected = it
        loadProducts()
    }

    fun loadData(baseFragment: BaseFragment) {
        var orderDTO = baseFragment.loadExtraDTO() as OrderDTO?
        if (orderDTO == null) {
            orderDTO = OrderDTO()
        } else {
            orderDTO.isNew = false
        }
        orderDTO.let { _order.postValue(it) }
    }

    fun loadLastOrders() {
        viewModelScope.launch {
            _myOrders.postValue(listOf())
        }
    }

    fun loadAddress() {
        viewModelScope.launch {
            val address = addressUseCase.loadAddress(requireActivityContext())
            address.let {
                _addressFields.postValue(it)
            }
        }
    }

    fun searchByCep(cep: String) {
        viewModelScope.launch {
            if (cep.isCEPValid() && cep != addressFields.value?.cep) {
                addressUseCase.searchAddressByCEP(
                    cep = cep,
                    onSuccess = {
                        addressFields.value.apply {
                            this?.cep = cep
                            this?.street = it?.street
                            this?.district = it?.district
                        }
                        _addressFields.postValue(addressFields.value)
                    },
                    onError = { showError(it) }
                )
            }
        }
    }

    fun confirmAddress(
        cep: String,
        street: String,
        number: String,
        complement: String,
        district: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        viewModelScope.launch {

            addressFields.value.apply {
                this?.cep = cep
                this?.street = street
                this?.number = number
                this?.complement = complement
                this?.district = district
            }
            addressFields.value.let {
                if (it != null && validateAddress(it)) {
                    addressUseCase.updateAddress(requireActivityContext(), it)
                    onSuccess.invoke()
                } else {
                    onError.invoke()
                }
            }
        }
    }

    private fun validateAddress(addressDTO: AddressDTO): Boolean {
        val inputForm = AddressInputForm()
        var isAddressValid = true

        if (!addressDTO.cep.isCEPValid()) {
            isAddressValid = false
            inputForm.cepError = getString(R.string.cep_invalid)
        }
        if (addressDTO.street.isNullOrBlank()) {
            isAddressValid = false
            inputForm.streetError = getString(R.string.street_invalid)
        }
        if (addressDTO.district.isNullOrBlank()) {
            isAddressValid = false
            inputForm.districtError = getString(R.string.district_invalid)
        }
        if (!isAddressValid) {
            _addressInputForm.value = inputForm
        }
        return isAddressValid
    }

    private fun String?.isCEPValid(): Boolean {
        return this?.length == 8
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
}