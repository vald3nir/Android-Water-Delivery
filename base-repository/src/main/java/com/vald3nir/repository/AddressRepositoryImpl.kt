package com.vald3nir.repository

import android.content.Context
import com.vald3nir.core_repository.storage.loadDataString
import com.vald3nir.core_repository.storage.saveDataString
import com.vald3nir.core_repository.toDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class AddressRepositoryImpl : AddressRepository {

    interface AddressAPI {
        @GET("/v1/cep/{cep}")
        fun getAddressByCEP(@Path("cep") cep: String?): Call<com.vald3nir.repository.dtos.AddressDTO>
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.postmon.com.br")
        .build()

    override suspend fun searchAddressByCEP(
        cep: String,
        onSuccess: (com.vald3nir.repository.dtos.AddressDTO?) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val service: AddressAPI = retrofit.create(AddressAPI::class.java)
        service.getAddressByCEP(cep).enqueue(object : Callback<com.vald3nir.repository.dtos.AddressDTO> {
            override fun onResponse(call: Call<com.vald3nir.repository.dtos.AddressDTO>, response: Response<com.vald3nir.repository.dtos.AddressDTO>) {
                onSuccess.invoke(response.body())
            }

            override fun onFailure(call: Call<com.vald3nir.repository.dtos.AddressDTO>, t: Throwable) {
                onError.invoke(Exception(t.message))
            }
        })
    }

    override suspend fun loadAddress(context: Context?): com.vald3nir.repository.dtos.AddressDTO {
        val json: String? = context?.loadDataString("Address")
        return if (json.isNullOrBlank()) com.vald3nir.repository.dtos.AddressDTO()
        else json.toDTO(com.vald3nir.repository.dtos.AddressDTO::class.java)
    }

    override suspend fun updateAddress(context: Context?, address: com.vald3nir.repository.dtos.AddressDTO?) {
        context?.saveDataString("Address", address?.toJson())
    }
}