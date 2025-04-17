package org.tekeli.borisp

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectSpy
import jakarta.inject.Inject
import org.apache.kafka.clients.producer.KafkaProducer
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doThrow
import org.mockito.kotlin.argThat

@QuarkusTest
class TemperatureMeasurementKafkaConsumerRetryAsyncQuarkusTest {
    @Inject
    @ConfigProperty(name = "kafka.bootstrap.servers")
    lateinit var bootstrapServers: String

    @ConfigProperty(name = "mp.messaging.incoming.temperature-measurements.topic")
    lateinit var temperatureMeasurementsTopic: String

    @InjectSpy
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
    fun `async recovery when a specific measurement encounters a temporary exception, retry mechanism resolves it while second measurement processes independently`() {
        val producerRecord =
            givenProducerRecord(temperatureMeasurementsTopic, "Berlin", givenTemperatureMeasurementAsJson("Berlin", 22.3))
        val otherProducerRecord =
            givenProducerRecord(temperatureMeasurementsTopic, "München", givenTemperatureMeasurementAsJson("München", 24.9))
        doThrow(RuntimeException("Boom!!!"))
            .doCallRealMethod()
            .`when`(temperatureMeasurementService).save(argThat { city == "Berlin" })
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        givenTestKafkaProducer.send(producerRecord)
        givenTestKafkaProducer.send(otherProducerRecord)

        await().untilAsserted({ assertThat(temperatureMeasurementService.getAll()).hasSize(2) })
        assertThat(temperatureMeasurementService.getAll().get(0).city).isEqualTo("München")
        assertThat(temperatureMeasurementService.getAll().get(1).city).isEqualTo("Berlin")
    }
}