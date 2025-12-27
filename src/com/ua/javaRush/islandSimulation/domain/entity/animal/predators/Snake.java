package com.ua.javaRush.islandSimulation.domain.entity.animal.predators;

import com.ua.javaRush.islandSimulation.domain.entity.animal.Predator;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.util.List;

public class Snake extends Predator {
    public Snake (List<String> parameters, Cell cell) {
        super(parameters, cell);
        setType("Snake");
    }
}
