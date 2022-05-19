package com.vald3nir.diskwater.presentation.address

import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.common.core.BaseViewModel
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.address.AddressUseCase
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import kotlinx.coroutines.launch

class AddressViewModel(
    private val screenNavigation: ScreenNavigation,
    private val authUseCase: AuthUseCase,
    private val addressUseCase: AddressUseCase,
) : BaseViewModel() {

    private fun checkAddress() {
        viewModelScope.launch {
            addressUseCase.searchAddressByCEP(cep = "60320250", onSuccess = {
                println(it)
            }, onError = {})
        }
    }


}