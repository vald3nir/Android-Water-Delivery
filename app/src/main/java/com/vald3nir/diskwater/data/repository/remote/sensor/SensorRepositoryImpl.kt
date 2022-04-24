package com.vald3nir.diskwater.data.repository.remote.sensor

import com.google.firebase.firestore.FirebaseFirestore
import com.vald3nir.diskwater.data.dto.SensorListDTO
import com.vald3nir.diskwater.data.dto.toSensorListDTO


class SensorRepositoryImpl : SensorRepository {

    override suspend fun updateSensorList(
        userID: String,
        sensorList: SensorListDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {

        FirebaseFirestore.getInstance()
            .collection(userID)
            .document("sensor_list")
            .set(sensorList.toHashMap())
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onError.invoke(it)
            }

    }

    override suspend fun getSensorList(
        userID: String,
        onSuccess: (sensorList: SensorListDTO?) -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        FirebaseFirestore.getInstance()
            .collection(userID)
            .document("sensor_list")
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.data?.toSensorListDTO())
            }
            .addOnFailureListener {
                onError.invoke(it)
            }
    }
}