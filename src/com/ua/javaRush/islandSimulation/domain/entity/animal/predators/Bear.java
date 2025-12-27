package com.ua.javaRush.islandSimulation.domain.entity.animal.predators;

import com.ua.javaRush.islandSimulation.domain.entity.animal.Predator;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.util.List;

public class Bear extends Predator {
    public Bear (List<String> parameters, Cell cell) {
        super(parameters, cell);
        setType("Bear");
    }
}