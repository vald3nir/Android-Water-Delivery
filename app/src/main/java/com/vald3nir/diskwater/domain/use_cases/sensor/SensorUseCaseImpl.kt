package com.vald3nir.diskwater.domain.use_cases.sensor

import com.vald3nir.diskwater.data.dto.SensorListDTO
import com.vald3nir.diskwater.data.repository.remote.sensor.SensorRepository

class SensorUseCaseImpl(
    private val repository: SensorRepository
) : SensorUseCase {

    override suspend fun updateSensorList(
        userID: String,
        sensorList: SensorListDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        repository.updateSensorList(userID, sensorList, onSuccess, onError)
    }

    override suspend fun getSensorList(
        userID: String,
        onSuccess: (sensorList: SensorListDTO?) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        repository.getSensorList(userID, onSuccess, onError)
    }
}