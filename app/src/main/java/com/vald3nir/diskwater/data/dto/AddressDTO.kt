package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
class AddressDTO(
    @SerializedName("estado") var state: String? = null,
    @SerializedName("cidade") var city: String? = null,
    @SerializedName("bairro") var district: String?,
    @SerializedName("logradouro") var street: String?,
    var number: String?,
    var complement: String?,
    var cep: String?,
) : BaseDTO()