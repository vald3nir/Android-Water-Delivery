package com.vald3nir.core.repository.dtos

import com.vald3nir.core_repository.BaseDTO

data class ClientDTO(
    var name: String? = null,
    var userID: String? = null,
    var photo: String? = null,
    var telephone: String? = null,
    var email: String? = null,
) : BaseDTO()