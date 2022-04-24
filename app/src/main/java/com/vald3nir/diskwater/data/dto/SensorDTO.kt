package com.vald3nir.diskwater.data.dto

data class SensorDTO(
    val id: String? = null,
    val alias: String? = null,
    val enable: Boolean = false,
) : BaseDTO()