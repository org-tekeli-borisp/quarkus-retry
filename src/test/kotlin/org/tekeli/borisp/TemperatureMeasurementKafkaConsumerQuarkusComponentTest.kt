package org.tekeli.borisp

import io.quarkus.test.InjectMock
import io.quarkus.test.component.QuarkusComponentTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify

@QuarkusComponentTest
class TemperatureMeasurementKafkaConsumerQuarkusComponentTest {

    @InjectMock
    lateinit var temperatureMeasurementService: TemperatureMeasurementService

    @Inject
    lateinit var sus: TemperatureMeasurementKafkaConsumer

    @Test
    fun `should consume ConsumerRecord`() {

        val consumerRecord = givenConsumerRecord(givenTemperatureMeasurement("Essen", 22.8))

        assertThatNoException().isThrownBy { sus.consume(consumerRecord) }
    }

    @Test
    fun `should save TemperatureMeasurement`() {
        val consumerRecord = givenConsumerRecord(givenTemperatureMeasurement("Magdeburg", 20.1))

        sus.consume(consumerRecord)

        verify(temperatureMeasurementService).save(consumerRecord.value())
    }
}