package org.tekeli.borisp

import org.apache.kafka.clients.consumer.ConsumerRecord
import java.time.OffsetDateTime

fun givenConsumerRecord(): ConsumerRecord<String, TemperatureMeasurement> {
    val topic = "topic"
    val temperatureMeasurement = givenTemperatureMeasurement()
    return ConsumerRecord(topic, 0, 0, temperatureMeasurement.city, temperatureMeasurement)
}

fun givenTemperatureMeasurement(): TemperatureMeasurement =
    TemperatureMeasurement("Hamburg", 18.7, OffsetDateTime.parse("2025-04-15T00:00:00Z"))

fun givenOtherTemperatureMeasurement(): TemperatureMeasurement =
    TemperatureMeasurement("Kiel", 17.5, OffsetDateTime.parse("2025-04-15T00:00:00Z"))

