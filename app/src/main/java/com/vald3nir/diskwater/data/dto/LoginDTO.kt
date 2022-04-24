package com.vald3nir.diskwater.data.dto

data class LoginDTO(
    val email: String? = null,
    val password: String? = null,
    val rememberLogin: Boolean = false,
) : BaseDTO()