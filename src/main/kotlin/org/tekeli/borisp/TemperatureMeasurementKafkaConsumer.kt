package org.tekeli.borisp

import io.quarkus.logging.Log
import io.smallrye.reactive.messaging.annotations.Blocking
import jakarta.enterprise.context.Dependent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.eclipse.microprofile.faulttolerance.Retry
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.time.temporal.ChronoUnit

@Dependent
open class TemperatureMeasurementKafkaConsumer(val temperatureMeasurementService: TemperatureMeasurementService) {
    @Incoming("temperature-measurements")
    @Retry(maxRetries = 3, delay = 1000L, delayUnit = ChronoUnit.MILLIS)
    @Blocking(ordered = false)
    open fun consume(consumerRecord: ConsumerRecord<String, TemperatureMeasurement>) {
        Log.info("Consuming record: $consumerRecord")
        temperatureMeasurementService.save(consumerRecord.value())
    }
}
