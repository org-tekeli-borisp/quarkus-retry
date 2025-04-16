package org.tekeli.borisp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TemperatureMeasurementServiceTest {

    lateinit var temperatureMeasurementService: TemperatureMeasurementService

    @BeforeEach
    fun setUp() {
        temperatureMeasurementService = TemperatureMeasurementService()
    }

    @Test
    fun `save should increase the temperature measurand collection by one`() {
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        temperatureMeasurementService.save(givenTemperatureMeasurement("Norderstedt", 17.9))

        assertThat(temperatureMeasurementService.getAll()).hasSize(1)
    }

    @Test
    fun `saving should be idempotent`() {
        val temperatureMeasurement = givenTemperatureMeasurement("Neumünster", 17.3)
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        temperatureMeasurementService.save(temperatureMeasurement)
        temperatureMeasurementService.save(temperatureMeasurement)

        assertThat(temperatureMeasurementService.getAll()).hasSize(1)
    }

    @Test
    fun `saving of two different TemperatureMeasurements increases the collection's size by two`() {
        val temperatureMeasurement1 = givenTemperatureMeasurement("Freiburg", 25.0)
        val temperatureMeasurement2 =  givenTemperatureMeasurement("Kassel", 24.2)
        assertThat(temperatureMeasurementService.getAll()).hasSize(0)

        temperatureMeasurementService.save(temperatureMeasurement1)
        temperatureMeasurementService.save(temperatureMeasurement2)

        assertThat(temperatureMeasurementService.getAll()).hasSize(2)
    }

    @Test
    fun `clear should empty the temperature measurand collection`() {
        temperatureMeasurementService.save(givenTemperatureMeasurement("Soltau", 22.1))
        assertThat(temperatureMeasurementService.getAll()).hasSize(1)

        temperatureMeasurementService.clear()

        assertThat(temperatureMeasurementService.getAll()).hasSize(0)
    }
}