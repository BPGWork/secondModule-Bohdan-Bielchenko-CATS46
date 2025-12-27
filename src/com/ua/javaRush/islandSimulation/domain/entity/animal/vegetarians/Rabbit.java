package com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians;

import com.ua.javaRush.islandSimulation.domain.entity.animal.Vegetarian;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.util.List;

public class Rabbit extends Vegetarian {
    public Rabbit(List<String> parameters, Cell cell) {
        super(parameters, cell);
        setType("Rabbit");
    }
}
