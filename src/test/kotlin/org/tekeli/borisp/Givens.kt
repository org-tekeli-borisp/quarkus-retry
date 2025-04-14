package org.tekeli.borisp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.time.OffsetDateTime
import java.util.*

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

fun givenTestKafkaProducer(bootstrapServers: String): KafkaProducer<String, String> {
    val config = Properties()
    config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
    config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.canonicalName
    config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.canonicalName
    return KafkaProducer<String, String>(config)
}

fun givenProducerRecord(topic: String): ProducerRecord<String, String> =
    ProducerRecord(topic, null, "Hamburg", givenTemperatureMeasurementAsJson())
