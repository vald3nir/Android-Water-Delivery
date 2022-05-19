package com.vald3nir.diskwater.presentation.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.core.BaseViewModel
import com.vald3nir.diskwater.data.dto.AddressDTO
import com.vald3nir.diskwater.data.form.AddressInputForm
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.address.AddressUseCase
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import kotlinx.coroutines.launch

class AddressViewModel(
    private val screenNavigation: ScreenNavigation,
    private val authUseCase: AuthUseCase,
    private val addressUseCase: AddressUseCase,
) : BaseViewModel() {

    private val _addressInputForm = MutableLiveData<AddressInputForm>()
    val addressInputForm: LiveData<AddressInputForm> = _addressInputForm

    private val _addressFields = MutableLiveData<AddressDTO>()
    val addressFields: LiveData<AddressDTO> = _addressFields

    fun loadAddress() {
        showLoading(true)
        viewModelScope.launch {
            val address = addressUseCase.loadAddress()
            address.let {
                _addressFields.postValue(it)
                showLoading(false)
            }
        }
    }

    private var lastCEP = ""
    fun searchByCep(cep: String) {
        viewModelScope.launch {
            if (cep.isCEPValid() && cep != lastCEP) {
                addressUseCase.searchAddressByCEP(
                    cep = cep,
                    onSuccess = {
                        lastCEP = cep
                        _addressFields.postValue(it)
                    },
                    onError = {}
                )
            }
        }
    }

    fun saveAddress(addressDTO: AddressDTO) {
        viewModelScope.launch {
            if (checkAddressFields(addressDTO)) {
                addressUseCase.updateAddress(addressDTO)
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

    fun String?.isCEPValid(): Boolean {
        return this?.length == 8
    }
}