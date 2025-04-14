package org.tekeli.borisp

import io.quarkus.logging.Log
import jakarta.enterprise.context.Dependent
import jakarta.inject.Inject
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.eclipse.microprofile.reactive.messaging.Incoming

@Dependent
class TemperatureMeasurementKafkaConsumer() {

    @Inject
    lateinit var temperatureMeasurementService: TemperatureMeasurementService

    @Incoming("temperature-measurements")
    fun consume(consumerRecord: ConsumerRecord<String, TemperatureMeasurement>) {
        Log.info("Consuming record: $consumerRecord")
        temperatureMeasurementService.save(consumerRecord.value())
    }
}
