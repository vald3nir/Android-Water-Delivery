package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
class AddressDTO(
    @SerializedName("estado") var state: String? = null,
    @SerializedName("cidade") var city: String? = null,
    @SerializedName("bairro") var district: String? = null,
    @SerializedName("logradouro") var street: String? = null,
    var number: String? = null,
    var complement: String? = null,
    var cep: String? = null,
) : BaseDTO()