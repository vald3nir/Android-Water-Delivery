package com.vald3nir.diskwater.data.dto

class ClientDTO(
    var name: String? = null,
    var photo: String? = null,
    var telephone: String? = null,
    var email: String? = null,
    var address: AddressDTO? = null,
) : BaseDTO()