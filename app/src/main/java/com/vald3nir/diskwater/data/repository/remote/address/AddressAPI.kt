package com.vald3nir.diskwater.data.repository.remote.address

import com.vald3nir.diskwater.data.dto.AddressDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AddressAPI {

    @GET("/v1/cep/{cep}")
    fun getAddressByCEP(@Path("cep") cep: String?): Call<AddressDTO>

}