package com.ua.javaRush.islandSimulation.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// This class is responsible for importing parameters into the project
public class EntityDto {
    public String type;
    public List<String> parameters;

    private static final String RESOURCE_PATH = "entityParameters.json";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final EntityWrapper CACHED = addParemeters();

    public EntityDto () {}

    public EntityDto (String type, List<String> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    // This method reads parameters from a json file and writes them to a List
    public static EntityWrapper addParemeters () {
        ClassLoader cl = EntityDto.class.getClassLoader();

        try (InputStream is = cl.getResourceAsStream(RESOURCE_PATH)) {
            if (is == null) {
                throw new IllegalStateException(
                        "entityParameters.json not found in classpath: " + RESOURCE_PATH
                );
            }
            return MAPPER.readValue(is, EntityWrapper.class);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Cannot load entity parameters from classpath: " + RESOURCE_PATH, e
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof EntityDto)) {
            return false;
        }

        EntityDto entityDto = (EntityDto) o;
        if (!(type.equals(entityDto.type))) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
