package com.vald3nir.diskwater.data.repository.remote.consumption

import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.vald3nir.diskwater.common.extensions.toConsumptionRealTimeDTO
import com.vald3nir.diskwater.data.dto.ConsumptionRealTimeDTO
import java.util.*

class ConsumptionRepositoryImpl : ConsumptionRepository {

    companion object {
        const val BROKEN_ADDRESS = "broker.hivemq.com"
    }

    private val clientMQTT = Mqtt5Client.builder()
        .identifier(UUID.randomUUID().toString())
        .serverHost(BROKEN_ADDRESS)
        .automaticReconnect()
        .applyAutomaticReconnect()
        .buildAsync()

    init {
        clientMQTT.connect()
    }

    override suspend fun subscriberConsumptionRealTime(
        topic: String,
        onResponse: (consumptionRealTimeDTO: ConsumptionRealTimeDTO) -> Unit
    ) {
        clientMQTT.toAsync().subscribeWith()
            .topicFilter(topic)
            .qos(MqttQos.EXACTLY_ONCE)
            .callback(({ message ->
                run {
                    onResponse.invoke(String(message.payloadAsBytes).toConsumptionRealTimeDTO())
                }
            })).send()
    }
}