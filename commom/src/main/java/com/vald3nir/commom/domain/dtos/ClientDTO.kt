package com.vald3nir.commom.domain.dtos

import com.vald3nir.repository.BaseDTO

data class ClientDTO(
    var name: String? = null,
    var userID: String? = null,
    var photo: String? = null,
    var telephone: String? = null,
    var email: String? = null,
    var address: AddressDTO? = null,
) : BaseDTO()