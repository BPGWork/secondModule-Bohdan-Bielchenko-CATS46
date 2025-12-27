package com.ua.javaRush.islandSimulation.domain.entity.animal.predators;

import com.ua.javaRush.islandSimulation.domain.entity.animal.Predator;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.util.List;

public class Fox extends Predator {
    public Fox (List<String> parameters, Cell cell) {
        super(parameters, cell);
        setType("Fox");
    }
}
