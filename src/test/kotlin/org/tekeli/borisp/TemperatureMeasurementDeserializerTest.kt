package org.tekeli.borisp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.text.Charsets.UTF_8

class TemperatureMeasurementDeserializerTest {
    lateinit var sus: TemperatureMeasurementDeserializer

    @BeforeEach
    fun setUp() {
        sus = TemperatureMeasurementDeserializer()
        sus.registerJavaTimeModule()
    }

    @Test
    fun `should deserialize temperatureMeasurement`() {
        val deserializedTemperatureMeasurement =
            sus.deserialize("topic", givenTemperatureMeasurementAsJson("Lübeck", 14.1).toByteArray(UTF_8))

        assertThat(deserializedTemperatureMeasurement).isNotNull
        assertThat(deserializedTemperatureMeasurement.city).isEqualTo("Lübeck")
        assertThat(deserializedTemperatureMeasurement.temperature).isEqualTo(14.1)
        assertThat(deserializedTemperatureMeasurement.timestamp.toString()).isEqualTo("2025-04-15T01:02:03Z")
    }
}