package org.tekeli.borisp

import io.quarkus.logging.Log
import io.smallrye.common.annotation.RunOnVirtualThread
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.eclipse.microprofile.faulttolerance.Retry
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.time.temporal.ChronoUnit

@ApplicationScoped
open class TemperatureMeasurementKafkaConsumer() {
    @Inject
    private lateinit var temperatureMeasurementService: TemperatureMeasurementService

    @Incoming("temperature-measurements")
    @Retry(maxRetries = 3, delay = 1000L, delayUnit = ChronoUnit.MILLIS)
    @RunOnVirtualThread
    open fun consume(consumerRecord: ConsumerRecord<String, TemperatureMeasurement>) {
        Log.info("Consuming record: $consumerRecord")
        temperatureMeasurementService.save(consumerRecord.value())
    }
}
