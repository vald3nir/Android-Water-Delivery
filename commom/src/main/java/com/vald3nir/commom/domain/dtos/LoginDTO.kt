package com.vald3nir.commom.domain.dtos

import com.vald3nir.repository.BaseDTO

data class LoginDTO(
    var email: String?,
    var password: String?,
    var rememberLogin: Boolean = false,
) : BaseDTO()