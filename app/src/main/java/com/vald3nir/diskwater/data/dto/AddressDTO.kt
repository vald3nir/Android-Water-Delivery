package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
class AddressDTO(
    @SerializedName("estado") var state: String?,
    @SerializedName("cidade") var city: String?,
    @SerializedName("bairro") var district: String?,
    @SerializedName("logradouro") var street: String?,
    var number: String?,
    var cep: String?,
) : BaseDTO()