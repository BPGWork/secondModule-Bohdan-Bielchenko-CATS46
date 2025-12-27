package com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians;

import com.ua.javaRush.islandSimulation.domain.entity.animal.Vegetarian;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.util.List;

public class Caterpillar extends Vegetarian {
    public Caterpillar (List<String> parameters, Cell cell) {
        super(parameters, cell);
        setType("Caterpillar");
    }
}
