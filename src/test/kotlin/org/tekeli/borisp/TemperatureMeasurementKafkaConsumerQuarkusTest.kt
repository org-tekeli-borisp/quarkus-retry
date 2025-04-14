package org.tekeli.borisp

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.apache.kafka.clients.producer.KafkaProducer
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@QuarkusTest
class TemperatureMeasurementKafkaConsumerQuarkusTest {
    @Inject
    @ConfigProperty(name = "kafka.bootstrap.servers")
    lateinit var bootstrapServers: String

    @ConfigProperty(name = "mp.messaging.incoming.temperature-measurements.topic")
    lateinit var temperatureMeasurementsTopic: String

    @Inject
    lateinit var temperatureMeasurementService: TemperatureMeasurementService

    lateinit var givenTestKafkaProducer: KafkaProducer<String, String>

    @BeforeEach
    fun setUp() {
        givenTestKafkaProducer = givenTestKafkaProducer(bootstrapServers)
    }

    @AfterEach
    fun tearDown() {
        givenTestKafkaProducer.close()
    }

    @Test
    fun `a new TemperatureMeasurement on kafka topic increases the collection's size by one`() {
        val producerRecord = givenProducerRecord(temperatureMeasurementsTopic)
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        givenTestKafkaProducer.send(producerRecord)

        await().untilAsserted({ assertThat(temperatureMeasurementService.getAll()).hasSize(1) })
    }
}