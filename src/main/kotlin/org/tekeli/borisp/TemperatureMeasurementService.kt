package org.tekeli.borisp

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TemperatureMeasurementService() {
    fun save(temperatureMeasurement: TemperatureMeasurement) {
        Log.info("Saving temperature measurement $temperatureMeasurement")
    }
}
