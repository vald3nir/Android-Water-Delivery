package com.vald3nir.sales.domain.use_cases

import android.content.Context
import com.vald3nir.core.repository.AddressRepository

class AddressUseCaseImpl(
    private val repository: AddressRepository,
) : AddressUseCase {

    override suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (com.vald3nir.core.repository.dtos.AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        repository.searchAddressByCEP(cep, onSuccess, onError)
    }

    override suspend fun loadAddress(context: Context?): com.vald3nir.core.repository.dtos.AddressDTO {
        return repository.loadAddress(context)
    }

    override suspend fun updateAddress(context: Context?, address: com.vald3nir.core.repository.dtos.AddressDTO?) {
        repository.updateAddress(context, address)
    }
}