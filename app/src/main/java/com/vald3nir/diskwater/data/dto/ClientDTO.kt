package com.vald3nir.diskwater.data.dto

import com.vald3nir.toolkit.data.dto.BaseDTO

class ClientDTO(
    var name: String? = null,
    var userID: String? = null,
    var photo: String? = null,
    var telephone: String? = null,
    var email: String? = null,
    var address: AddressDTO? = null,
) : BaseDTO()