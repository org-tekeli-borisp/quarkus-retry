package org.tekeli.borisp

import io.quarkus.test.component.QuarkusComponentTest
import jakarta.inject.Inject
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

@QuarkusComponentTest
class TemperatureMeasurementKafkaConsumerTest {

    @Inject
    lateinit var sus: TemperatureMeasurementKafkaConsumer

    @Test
    fun shouldConsumeConsumerRecord() {
        val consumerRecord = mock<ConsumerRecord<String, TemperatureMeasurement>>()

        assertThatNoException().isThrownBy { sus.consume(consumerRecord) }
    }
}