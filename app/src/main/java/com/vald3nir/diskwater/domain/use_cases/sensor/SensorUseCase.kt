package com.vald3nir.diskwater.domain.use_cases.sensor

import com.vald3nir.diskwater.data.dto.SensorListDTO

interface SensorUseCase {

    suspend fun updateSensorList(
        userID: String,
        sensorList: SensorListDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun getSensorList(
        userID: String,
        onSuccess: (sensorList: SensorListDTO?) -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}