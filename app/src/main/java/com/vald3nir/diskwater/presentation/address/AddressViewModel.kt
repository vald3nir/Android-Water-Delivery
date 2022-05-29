package com.vald3nir.diskwater.presentation.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.toolkit.core.CoreViewModel
import com.vald3nir.diskwater.data.dto.AddressDTO
import com.vald3nir.diskwater.data.form.AddressInputForm
import com.vald3nir.diskwater.domain.use_cases.address.AddressUseCase
import kotlinx.coroutines.launch

class AddressViewModel(
    private val addressUseCase: AddressUseCase,
) : CoreViewModel() {

    private val _addressInputForm = MutableLiveData<AddressInputForm>()
    val addressInputForm: LiveData<AddressInputForm> = _addressInputForm

    private val _addressFields = MutableLiveData<AddressDTO>()
    val addressFields: LiveData<AddressDTO> = _addressFields

    fun loadAddress() {
        viewModelScope.launch {
            val address = addressUseCase.loadAddress()
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

    fun saveAddress(
        cep: String,
        street: String,
        number: String,
        complement: String,
        district: String,
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
                if (it != null && checkAddressFields(it)) {
                    addressUseCase.updateAddress(it)
                    showMessage(getString(R.string.address_successfully_updated))
                    finish()
                }
            }
        }
    }

    private fun checkAddressFields(addressDTO: AddressDTO): Boolean {
        var isValid = true
        val inputForm = AddressInputForm()

        if (!addressDTO.cep.isCEPValid()) {
            isValid = false
            inputForm.cepError = getString(R.string.cep_invalid)
        }
        if (addressDTO.street.isNullOrBlank()) {
            isValid = false
            inputForm.streetError = getString(R.string.street_invalid)
        }
        if (addressDTO.district.isNullOrBlank()) {
            isValid = false
            inputForm.districtError = getString(R.string.district_invalid)
        }
        if (!isValid) {
            _addressInputForm.value = inputForm
        }
        return isValid
    }

    private fun String?.isCEPValid(): Boolean {
        return this?.length == 8
    }
}