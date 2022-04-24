package com.vald3nir.diskwater.data.repository.remote.consumption

import com.vald3nir.diskwater.data.dto.ConsumptionRealTimeDTO

interface ConsumptionRepository {

    suspend fun subscriberConsumptionRealTime(
        topic: String,
        onResponse: (consumptionRealTimeDTO: ConsumptionRealTimeDTO) -> Unit
    )
}