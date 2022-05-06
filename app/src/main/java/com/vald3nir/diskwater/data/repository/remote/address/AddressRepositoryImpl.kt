package com.vald3nir.diskwater.data.repository.remote.address

import com.vald3nir.diskwater.data.dto.AddressDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AddressRepositoryImpl() : AddressRepository {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.postmon.com.br")
        .build()

    override suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val service: AddressAPI = retrofit.create(AddressAPI::class.java)
        service.getAddressByCEP(cep).enqueue(object : Callback<AddressDTO> {
            override fun onResponse(call: Call<AddressDTO>, response: Response<AddressDTO>) {
                onSuccess.invoke(response.body())
            }
            override fun onFailure(call: Call<AddressDTO>, t: Throwable) {
                onError.invoke(Exception(t.message))
            }
        })
    }
}