package com.vald3nir.diskwater.data.dto

import com.vald3nir.toolkit.data.dto.BaseDTO

data class LoginDTO(
    var email: String?,
    var password: String?,
    var rememberLogin: Boolean = false,
) : BaseDTO()