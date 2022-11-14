package com.vald3nir.core.repository.dtos

import com.vald3nir.core_repository.BaseDTO

data class LoginDTO(
    var email: String?,
    var password: String?,
    var rememberLogin: Boolean = false,
) : BaseDTO()