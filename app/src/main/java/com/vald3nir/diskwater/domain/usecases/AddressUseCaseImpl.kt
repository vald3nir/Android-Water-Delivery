package com.vald3nir.diskwater.domain.usecases

import android.content.Context
import com.vald3nir.diskwater.domain.dtos.AddressDTO
import com.vald3nir.diskwater.domain.repository.AddressRepository

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