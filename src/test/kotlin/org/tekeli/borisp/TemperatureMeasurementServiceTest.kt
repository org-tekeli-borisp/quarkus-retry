package org.tekeli.borisp

import io.quarkus.test.component.QuarkusComponentTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@QuarkusComponentTest
class TemperatureMeasurementServiceTest {

    @Inject
    lateinit var temperatureMeasurementService: TemperatureMeasurementService

    @Test
    fun `first saving of one TemperatureMeasurement leads to list size of one`() {
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        temperatureMeasurementService.save(TemperatureMeasurement())

        assertThat(temperatureMeasurementService.getAll()).hasSize(1)
    }

    @Test
    fun `two savings of two different TemperatureMeasurement's leads to list size of two`() {
        val temperatureMeasurement1 = TemperatureMeasurement()
        val temperatureMeasurement2 = TemperatureMeasurement()
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        temperatureMeasurementService.save(temperatureMeasurement1)
        temperatureMeasurementService.save(temperatureMeasurement2)

        assertThat(temperatureMeasurementService.getAll()).hasSize(2)
    }

    @Test
    fun `saving should be idempotent`() {
        val temperatureMeasurement = TemperatureMeasurement()
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        temperatureMeasurementService.save(temperatureMeasurement)
        temperatureMeasurementService.save(temperatureMeasurement)

        assertThat(temperatureMeasurementService.getAll()).hasSize(1)
    }
}