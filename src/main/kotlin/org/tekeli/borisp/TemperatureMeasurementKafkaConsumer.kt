package org.tekeli.borisp

import io.quarkus.logging.Log
import jakarta.enterprise.context.Dependent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.eclipse.microprofile.faulttolerance.Retry
import org.eclipse.microprofile.reactive.messaging.Incoming

@Dependent
class TemperatureMeasurementKafkaConsumer(val temperatureMeasurementService: TemperatureMeasurementService) {
    @Incoming("temperature-measurements")
    @Retry(maxRetries = 3)
    fun consume(consumerRecord: ConsumerRecord<String, TemperatureMeasurement>) {
        Log.info("Consuming record: $consumerRecord")
        temperatureMeasurementService.save(consumerRecord.value())
    }
}
