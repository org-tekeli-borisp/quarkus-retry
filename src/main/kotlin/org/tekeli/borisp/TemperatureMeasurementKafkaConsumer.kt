package org.tekeli.borisp

import io.quarkus.logging.Log
import jakarta.enterprise.context.Dependent
import jakarta.inject.Inject
import org.apache.kafka.clients.consumer.ConsumerRecord

@Dependent
class TemperatureMeasurementKafkaConsumer() {

    @Inject
    lateinit var temperatureMeasurementService: TemperatureMeasurementService

    fun consume(consumerRecord: ConsumerRecord<String, TemperatureMeasurement>) {
        Log.info("Consuming record: $consumerRecord")
        temperatureMeasurementService.save(consumerRecord.value())
    }
}
