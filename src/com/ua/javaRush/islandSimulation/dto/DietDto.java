package com.ua.javaRush.islandSimulation.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class DietDto {
    private static final String RESOURCE_PATH = "diet.json";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private DietDto () {}

    // Read and Save diet parameters
    public static DietWrapper createSpeciesMap () {
        DietWrapper dietWrapper = new DietWrapper();

        try (InputStream is = DietDto.class.getClassLoader().getResourceAsStream(RESOURCE_PATH)) {
            if (is == null) {
                throw new IllegalStateException("diet config not found in classpath: " + RESOURCE_PATH);
            }

            Map<String, Map<String, Integer>> raw =
                    MAPPER.readValue(is, new TypeReference<Map<String, Map<String, Integer>>>() {});

            dietWrapper.probabilityMap = raw;
            return dietWrapper;

        } catch (Exception e) {
            throw new RuntimeException("Cannot load diet config from classpath: " + RESOURCE_PATH, e);
        }
    }
}
