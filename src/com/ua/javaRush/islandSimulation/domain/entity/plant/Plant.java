package com.ua.javaRush.islandSimulation.domain.entity.plant;

import com.ua.javaRush.islandSimulation.domain.entity.Entity;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.util.List;
import java.util.Random;

// This class is responsible for implementing the plant
public class Plant extends Entity {
    private final Random random = new Random();

    private double weight;
    private double maxQuantity;
    private volatile double capacity;

    public Plant (List<String> parameters, Cell cell) {
        super(parameters, cell);
        weight = numberParameter(0);
        maxQuantity = numberParameter(3);
        capacity = random.nextDouble(0, maxQuantity);
        setType("Plant");
    }

    // Setter
    public void setCapacity (double capacity) {
        this.capacity = capacity;
    }

    // Getter
    public double getCapacity () {
        return capacity;
    }

    // Grow Plant
    public void grow () {
        if (capacity < maxQuantity) {
            capacity += random.nextDouble(0, maxQuantity);
        }
        if (capacity > maxQuantity) {
            capacity = maxQuantity;
        }
    }
}
