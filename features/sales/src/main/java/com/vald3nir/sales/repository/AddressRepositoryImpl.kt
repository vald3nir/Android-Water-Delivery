package com.vald3nir.sales.repository

import android.content.Context
import com.vald3nir.commom.domain.dtos.AddressDTO
import com.vald3nir.repository.storage.loadDataString
import com.vald3nir.repository.storage.saveDataString
import com.vald3nir.repository.toDTO
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
        fun getAddressByCEP(@Path("cep") cep: String?): Call<AddressDTO>
    }

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

    override suspend fun loadAddress(context: Context?): AddressDTO {
        val json: String? = context?.loadDataString("Address")
        return if (json.isNullOrBlank()) AddressDTO()
        else json.toDTO(AddressDTO::class.java)
    }

    override suspend fun updateAddress(context: Context?, address: AddressDTO?) {
        context?.saveDataString("Address", address?.toJson())
    }
}