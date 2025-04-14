package org.tekeli.borisp

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TemperatureMeasurementService() {

    private val temperatureMeasurements = mutableListOf<TemperatureMeasurement>()

    fun save(temperatureMeasurement: TemperatureMeasurement) {
        Log.info("Saving temperature measurement $temperatureMeasurement")
        temperatureMeasurements.add(temperatureMeasurement)
    }

    fun getAll(): List<TemperatureMeasurement> {
        return temperatureMeasurements
    }
}
