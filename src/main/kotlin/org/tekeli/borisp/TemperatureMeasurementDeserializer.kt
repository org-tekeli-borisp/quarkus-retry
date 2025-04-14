package org.tekeli.borisp

import com.fasterxml.jackson.core.type.TypeReference
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

class TemperatureMeasurementDeserializer() :
    ObjectMapperDeserializer<TemperatureMeasurement>(object : TypeReference<TemperatureMeasurement>() {})