package com.vald3nir.diskwater.common.extensions

import android.text.Editable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vald3nir.diskwater.data.dto.ConsumptionRealTimeDTO
import com.vald3nir.diskwater.data.dto.DeviceConsumptionDTO
import org.json.JSONArray

fun Double.toWattsSecond(): String {
    return "${this.toInt()} W/s"
}

fun Editable?.format(): String {
    return this.toString().trim()
}

fun String.toConsumptionRealTimeDTO(): ConsumptionRealTimeDTO {
    return Gson().fromJson(this, ConsumptionRealTimeDTO::class.java)
}

fun <T> String?.toDTO(classOfT: Class<T>): T {
    return Gson().fromJson(this, classOfT)
}

fun JSONArray.toListDeviceConsumptionDTO(): List<DeviceConsumptionDTO> {
    val type = object : TypeToken<List<DeviceConsumptionDTO?>?>() {}.type
    return Gson().fromJson(this.toString(), type)
}