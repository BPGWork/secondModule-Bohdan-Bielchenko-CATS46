package com.ua.javaRush.islandSimulation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

public class DietWrapper {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Map<String, Map<String, Integer>> probabilityMap;

    // get probability ( predator kill animal )
    public int getProbability(String predator, String prey) {
        Map<String, Integer> row = probabilityMap.get(predator);
        if (row == null) {
            return 0;
        }

        return row.getOrDefault(prey, 0);
    }
}
