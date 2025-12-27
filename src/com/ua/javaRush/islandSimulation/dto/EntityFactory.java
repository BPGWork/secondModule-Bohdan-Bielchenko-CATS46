package com.ua.javaRush.islandSimulation.dto;



import com.ua.javaRush.islandSimulation.domain.entity.Entity;
import com.ua.javaRush.islandSimulation.domain.entity.animal.predators.*;
import com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians.*;
import com.ua.javaRush.islandSimulation.domain.entity.plant.Plant;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.lang.reflect.Constructor;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class is responsible for creating entities
public class EntityFactory {
    private static Map<String, Class<? extends Entity>> REGISTRY = new HashMap<>();

    // This creates a list of entities by Type
    static {
        REGISTRY.put("Wolf", Wolf.class);
        REGISTRY.put("Bear", Bear.class);
        REGISTRY.put("Eagle", Eagle.class);
        REGISTRY.put("Snake", Snake.class);
        REGISTRY.put("Fox", Fox.class);
        REGISTRY.put("Boar", Boar.class);
        REGISTRY.put("Buffalo", Buffalo.class);
        REGISTRY.put("Caterpillar", Caterpillar.class);
        REGISTRY.put("Deer", Deer.class);
        REGISTRY.put("Duck", Duck.class);
        REGISTRY.put("Goat", Goat.class);
        REGISTRY.put("Horse", Horse.class);
        REGISTRY.put("Mouse", Mouse.class);
        REGISTRY.put("Rabbit", Rabbit.class);
        REGISTRY.put("Sheep", Sheep.class);
        REGISTRY.put("Plant", Plant.class);
    }

    // Method that creates an entity
    public static Entity createObject (EntityDto entityDto, Cell location) {
        Class<? extends Entity> clazz = REGISTRY.get(entityDto.type);

        if (clazz == null) {
            throw new IllegalArgumentException("Unknown entity type: " + entityDto.type);
        }

        try {
            Constructor<? extends Entity> constructor = clazz.getConstructor(List.class, Cell.class);

            return constructor.newInstance(entityDto.parameters, location);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create entity: " + entityDto.type, e);
        }
    }
}
