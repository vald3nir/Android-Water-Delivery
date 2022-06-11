package com.vald3nir.diskwater.domain.use_cases.address

import android.content.Context
import com.vald3nir.diskwater.data.dto.AddressDTO
import com.vald3nir.diskwater.data.repository.address.AddressRepository

class AddressUseCaseImpl(
    private val repository: AddressRepository,
) : AddressUseCase {

    override suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        repository.searchAddressByCEP(cep, onSuccess, onError)
    }

    override suspend fun loadAddress(context: Context?): AddressDTO {
        return repository.loadAddress(context)
    }

    override suspend fun updateAddress(context: Context?, address: AddressDTO?) {
        repository.updateAddress(context, address)
    }
}