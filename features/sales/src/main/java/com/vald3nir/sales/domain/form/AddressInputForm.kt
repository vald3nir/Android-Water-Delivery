package com.vald3nir.sales.domain.form

data class AddressInputForm(
    var cepError: String? = null,
    var streetError: String? = null,
    var districtError: String? = null,
)