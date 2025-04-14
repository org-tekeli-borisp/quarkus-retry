package org.tekeli.borisp

import io.quarkus.test.InjectMock
import io.quarkus.test.component.QuarkusComponentTest
import jakarta.inject.Inject
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify

@QuarkusComponentTest
class TemperatureMeasurementKafkaConsumerTest {

    @InjectMock
    lateinit var temperatureMeasurementService: TemperatureMeasurementService

    @Inject
    lateinit var sus: TemperatureMeasurementKafkaConsumer

    @Test
    fun `should consume ConsumerRecord`() {
        val consumerRecord = givenConsumerRecord()

        assertThatNoException().isThrownBy { sus.consume(consumerRecord) }
    }

    @Test
    fun `should save TemperatureMeasurement`() {
        val consumerRecord = givenConsumerRecord()

        sus.consume(consumerRecord)

        verify(temperatureMeasurementService).save(consumerRecord.value())
    }

    private fun givenConsumerRecord(): ConsumerRecord<String, TemperatureMeasurement> {
        val topic = "topic"
        val key = "Hamburg"
        val temperatureMeasurement = TemperatureMeasurement()
        return ConsumerRecord(topic, 0, 0, key, temperatureMeasurement)
    }
}