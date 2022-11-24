package com.vald3nir.diskwater.domain.dtos

import com.vald3nir.core_repository.BaseDTO

data class AddressDTO(
    var estado: String? = null,
    var cidade: String? = null,
    var bairro: String? = null,
    var logradouro: String? = null,
    var number: String? = null,
    var complement: String? = null,
    var cep: String? = null,
) : BaseDTO() {

    override fun toString(): String {
        var string = ""
        logradouro?.let { if (it.isNotBlank()) string += "$it, " }
        number?.let { if (it.isNotBlank()) string += "$it, " }
        complement?.let { if (it.isNotBlank()) string += "$it, " }
        bairro?.let { if (it.isNotBlank()) string += "$it, " }
        cidade?.let { if (it.isNotBlank()) string += " $it, " }
        cep?.let { if (it.isNotBlank()) string += it }
        return string
    }
}