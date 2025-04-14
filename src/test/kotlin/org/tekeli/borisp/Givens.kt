package org.tekeli.borisp

import org.apache.kafka.clients.consumer.ConsumerRecord

fun givenConsumerRecord(): ConsumerRecord<String, TemperatureMeasurement> {
    val topic = "topic"
    val key = "Hamburg"
    val temperatureMeasurement = TemperatureMeasurement()
    return ConsumerRecord(topic, 0, 0, key, temperatureMeasurement)
}

