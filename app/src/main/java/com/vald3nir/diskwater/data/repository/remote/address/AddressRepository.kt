package com.vald3nir.diskwater.data.repository.remote.address

import com.vald3nir.diskwater.data.dto.AddressDTO

interface AddressRepository {

    suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

}