package org.tekeli.borisp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class TemperatureMeasurement(
    @JsonProperty("city") val city: String,
    @JsonProperty("temperature") val temperature: Double,
    @JsonProperty("timestamp") val timestamp: OffsetDateTime
)
