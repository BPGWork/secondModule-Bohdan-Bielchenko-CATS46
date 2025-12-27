package com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians;

import com.ua.javaRush.islandSimulation.domain.entity.animal.Vegetarian;
import com.ua.javaRush.islandSimulation.domain.island.Cell;

import java.util.List;

public class Buffalo extends Vegetarian {
    public Buffalo (List<String> parameters, Cell cell) {
        super(parameters, cell);
        setType("Buffalo");
    }
}
