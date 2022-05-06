package com.vald3nir.diskwater.data.dto

import androidx.room.Entity

@Entity
data class LoginDTO(
    var email: String?,
    var password: String?,
    var rememberLogin: Boolean = false,
) : BaseDTO()