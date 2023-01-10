package com.vald3nir.diskwater.presentation.features.sales.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.app.base.BaseViewModel
import com.vald3nir.diskwater.domain.dtos.AddressDTO
import com.vald3nir.diskwater.domain.form.AddressInputForm
import com.vald3nir.diskwater.domain.usecases.AddressUseCase
import com.vald3nir.diskwater.domain.usecases.OrderUseCase
import kotlinx.coroutines.launch

class AddressViewModel(
    private val addressUseCase: AddressUseCase,
    private val orderUseCase: OrderUseCase,
) : BaseViewModel() {

    private val _addressInputForm = MutableLiveData<AddressInputForm>()
    val addressInputForm: LiveData<AddressInputForm> = _addressInputForm

    private val _addressFields = MutableLiveData<AddressDTO>()
    val addressFields: LiveData<AddressDTO> = _addressFields

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
                            this?.logradouro = it?.logradouro
                            this?.bairro = it?.bairro
                            this?.cidade = it?.cidade
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
                this?.logradouro = street
                this?.number = number
                this?.complement = complement
                this?.bairro = district
            }
            addressFields.value.let {
                if (it != null && validateAddress(it)) {
                    addressUseCase.updateAddress(requireActivityContext(), it)
                    orderUseCase.putAddress(it)
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
        if (addressDTO.logradouro.isNullOrBlank()) {
            isAddressValid = false
            inputForm.streetError = getString(R.string.street_invalid)
        }
        if (addressDTO.bairro.isNullOrBlank()) {
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
}