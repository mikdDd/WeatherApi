package com.example.weatherapi.utils;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Utility class for mapping JSON data to Java objects.
 */
public class JsonMapper {
    private JsonMapper(){}

    /**
     * Maps JSON data to the specified Java type using the provided JSON pointer.
     *
     * @param jsonString     The JSON string to be mapped.
     * @param typeReference  The type reference of the Java object to map to.
     * @param jsonPointer    The JSON pointer indicating the location of the data to be mapped.
     * @param <T>            The type of the Java object to map to.
     * @return               The Java object mapped from the JSON data.
     * @throws IOException   If an I/O error occurs during JSON processing.
     */
    public static <T> T mapJsonToType(String jsonString, TypeReference<T> typeReference, JsonPointer jsonPointer) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        jsonNode = jsonNode.at(jsonPointer);

        return objectMapper.readValue(jsonNode.traverse(), typeReference);
    }
}
