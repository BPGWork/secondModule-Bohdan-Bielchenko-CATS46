package com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians;

import com.ua.javaRush.islandSimulation.domain.entity.animal.Vegetarian;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.util.List;

public class Goat extends Vegetarian {
    public Goat (List<String> parameters, Cell cell) {
        super(parameters, cell);
        setType("Goat");
    }
}
