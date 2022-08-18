package com.vald3nir.sales.domain.use_cases

import android.content.Context
import com.vald3nir.repository.dtos.AddressDTO

interface AddressUseCase {

    suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (com.vald3nir.repository.dtos.AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun loadAddress(context: Context?): com.vald3nir.repository.dtos.AddressDTO

    suspend fun updateAddress(context: Context?, address: com.vald3nir.repository.dtos.AddressDTO?)

}