package com.vald3nir.diskwater.data.dto

import com.google.gson.annotations.SerializedName
import com.vald3nir.toolkit.data.dto.BaseDTO

class AddressDTO(
    @SerializedName("estado") var state: String? = null,
    @SerializedName("cidade") var city: String? = null,
    @SerializedName("bairro") var district: String? = null,
    @SerializedName("logradouro") var street: String? = null,
    var number: String? = null,
    var complement: String? = null,
    var cep: String? = null,
) : BaseDTO() {

    override fun toString(): String {
        var string = ""
        street?.let { string += "$it, " }
        number?.let { string += "$it, " }
        complement?.let { string += "$it, " }
        district?.let { string += "$it, " }
//        city?.let { string += " $it, " }
        cep?.let { string += it }
        return string
    }
}