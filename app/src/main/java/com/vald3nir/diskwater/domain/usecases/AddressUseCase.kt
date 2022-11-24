package com.vald3nir.diskwater.domain.usecases

import android.content.Context
import com.vald3nir.diskwater.domain.dtos.AddressDTO

interface AddressUseCase {

    suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun loadAddress(context: Context?): AddressDTO

    suspend fun updateAddress(context: Context?, address: AddressDTO?)

}