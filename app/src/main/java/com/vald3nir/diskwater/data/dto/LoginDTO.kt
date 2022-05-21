package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginDTO(
    @PrimaryKey(autoGenerate = false) var uid: Int = 1,
    var email: String?,
    var password: String?,
    var rememberLogin: Boolean = false,
) : BaseDTO()