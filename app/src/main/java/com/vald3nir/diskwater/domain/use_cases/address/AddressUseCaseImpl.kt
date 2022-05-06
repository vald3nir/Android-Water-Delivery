package com.vald3nir.diskwater.domain.use_cases.address

import com.vald3nir.diskwater.data.dto.AddressDTO
import com.vald3nir.diskwater.data.repository.local.daos.AddressDao
import com.vald3nir.diskwater.data.repository.remote.address.AddressRepository

class AddressUseCaseImpl(
    private val repository: AddressRepository,
    private val addressDao: AddressDao,
) : AddressUseCase {

    override suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        repository.searchAddressByCEP(cep, onSuccess, onError)
    }

    override suspend fun loadAddress(): AddressDTO? {
        return addressDao.first()
    }

    override suspend fun updateAddress(address: AddressDTO?) {
        addressDao.insert(address)
    }


}