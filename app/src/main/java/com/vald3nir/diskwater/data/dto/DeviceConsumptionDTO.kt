package com.vald3nir.diskwater.data.dto

data class DeviceConsumptionDTO(
    val consumption_kwh: Double,
    val date: String,
    val device: String
)