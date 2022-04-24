package com.vald3nir.diskwater.domain.use_cases.consumption

import com.vald3nir.diskwater.data.dto.ConsumptionRealTimeDTO

interface ConsumptionUseCase {
    suspend fun subscriberConsumptionRealTime(
        onResponse: (consumptionRealTimeDTO: ConsumptionRealTimeDTO) -> Unit
    )
}