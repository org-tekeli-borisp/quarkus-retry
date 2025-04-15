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
import org.mockito.kotlin.anyOrNull

@QuarkusTest
class TemperatureMeasurementKafkaConsumerRetryQuarkusTest {
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
    fun `a TemperatureMeasurement leads to a temporal exception during saving but retrying should remedy`() {
        val producerRecord = givenProducerRecord(temperatureMeasurementsTopic)
        doThrow(RuntimeException("Boom!!!"))
            .doCallRealMethod()
            .`when`(temperatureMeasurementService).save(anyOrNull())
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        givenTestKafkaProducer.send(producerRecord)

        await().untilAsserted({ assertThat(temperatureMeasurementService.getAll()).hasSize(1) })
    }
}