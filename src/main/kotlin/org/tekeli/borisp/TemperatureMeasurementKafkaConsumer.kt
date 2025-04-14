package org.tekeli.borisp

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TemperatureMeasurementKafkaConsumer {
    fun consume(record: Any) {
        Log.info("Consuming record: $record")
    }
}
