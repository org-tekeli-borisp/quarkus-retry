package org.tekeli.borisp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer
import org.apache.kafka.clients.consumer.ConsumerRecord
import java.time.OffsetDateTime

fun givenConsumerRecord(): ConsumerRecord<String, TemperatureMeasurement> {
    val topic = "topic"
    val temperatureMeasurement = givenTemperatureMeasurement()
    return ConsumerRecord(topic, 0, 0, temperatureMeasurement.city, temperatureMeasurement)
}

fun givenTemperatureMeasurement(): TemperatureMeasurement =
    TemperatureMeasurement("Hamburg", 18.7, OffsetDateTime.parse("2025-04-15T01:02:03Z"))

fun givenOtherTemperatureMeasurement(): TemperatureMeasurement =
    TemperatureMeasurement("Kiel", 17.5, OffsetDateTime.parse("2025-04-15T01:02:03Z"))

fun givenTemperatureMeasurementAsJson(): String = """
        {
            "city": "Hamburg",
            "temperature": 18.7,
            "timestamp": "2025-04-15T01:02:03Z"
        }
    """.trimIndent()

fun ObjectMapperDeserializer<TemperatureMeasurement>.registerJavaTimeModule() {
    val loadedClass = ObjectMapperDeserializer::class
    val declaredField = loadedClass.java.getDeclaredField("objectMapper")
    declaredField.isAccessible = true
    val objectMapper = declaredField.get(this) as ObjectMapper
    objectMapper.registerModule(JavaTimeModule())
    declaredField.isAccessible = false
}
