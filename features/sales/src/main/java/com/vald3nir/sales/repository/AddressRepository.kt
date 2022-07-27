package com.vald3nir.sales.repository

import android.content.Context
import com.vald3nir.commom.domain.dtos.AddressDTO

interface AddressRepository {

    suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun loadAddress(context: Context?): AddressDTO

    suspend fun updateAddress(context: Context?, address: AddressDTO?)
}