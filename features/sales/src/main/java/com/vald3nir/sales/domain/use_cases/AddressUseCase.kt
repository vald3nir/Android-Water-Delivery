package com.vald3nir.sales.domain.use_cases

import android.content.Context
import com.vald3nir.commom.domain.dtos.AddressDTO

interface AddressUseCase {

    suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun loadAddress(context: Context?): AddressDTO

    suspend fun updateAddress(context: Context?, address: AddressDTO?)

}