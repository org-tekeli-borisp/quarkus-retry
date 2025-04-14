package org.tekeli.borisp

import java.time.OffsetDateTime

class TemperatureMeasurement(
    val city: String,
    val temperature: Double,
    val timestamp: OffsetDateTime
)
