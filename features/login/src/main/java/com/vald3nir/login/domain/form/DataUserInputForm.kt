package com.vald3nir.login.domain.form

data class DataUserInputForm(
    var emailError: String? = null,
    var nameError: String? = null,
    var passwordError: String? = null,
    var confirmPasswordError: String? = null,
)