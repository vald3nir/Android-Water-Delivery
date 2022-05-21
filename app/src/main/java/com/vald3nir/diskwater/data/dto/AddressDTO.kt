package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class AddressDTO(
    @PrimaryKey(autoGenerate = false) var uid: Int = 1,
    @SerializedName("estado") var state: String? = null,
    @SerializedName("cidade") var city: String? = null,
    @SerializedName("bairro") var district: String? = null,
    @SerializedName("logradouro") var street: String? = null,
    var number: String? = null,
    var complement: String? = null,
    var cep: String? = null,
) : BaseDTO() {

    override fun toString(): String {
        return "$street, Nº $number, Complemento: $complement, $district"
    }

}