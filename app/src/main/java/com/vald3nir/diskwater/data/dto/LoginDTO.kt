package com.vald3nir.diskwater.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vald3nir.toolkit.data.BaseDTO

@Entity
data class LoginDTO(
    @PrimaryKey(autoGenerate = false) var key: Int = 1,
    var email: String?,
    var password: String?,
    var rememberLogin: Boolean = false,
) : BaseDTO()