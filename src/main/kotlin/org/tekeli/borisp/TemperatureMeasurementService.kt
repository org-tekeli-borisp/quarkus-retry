package org.tekeli.borisp

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
open class TemperatureMeasurementService() {

    private val temperatureMeasurements = mutableSetOf<TemperatureMeasurement>()

    fun save(temperatureMeasurement: TemperatureMeasurement) {
        Log.info("Saving temperature measurement $temperatureMeasurement")
        temperatureMeasurements.add(temperatureMeasurement)
    }

    fun getAll(): List<TemperatureMeasurement> {
        return temperatureMeasurements.toList()
    }

    fun clear() {
        temperatureMeasurements.clear()
    }
}
