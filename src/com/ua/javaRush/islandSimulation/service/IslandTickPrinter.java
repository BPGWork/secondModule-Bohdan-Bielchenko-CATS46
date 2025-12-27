package com.ua.javaRush.islandSimulation.service;

import com.ua.javaRush.islandSimulation.domain.entity.Emoji;
import com.ua.javaRush.islandSimulation.domain.island.CellManager;
import com.ua.javaRush.islandSimulation.domain.island.Island;

import java.util.*;
import java.util.concurrent.*;

public final class IslandTickPrinter {
    private Island island;

    private final Map<String, String> EMOJI = Emoji.getEMOJI();
    private int SIZE_X;
    private int SIZE_Y;
    private CellManager cm;

    protected IslandTickPrinter(Island island) {
        this.island = island;
        SIZE_X = island.getSizeX();
        SIZE_Y = island.getSizeY();
    }

    // Print info from tick
    public void print (String tick) {
        String TICK_INFO = "==============================TICK " + tick + " ==============================";

        System.out.println(TICK_INFO);
        System.out.println("Entity info: ");

        for (Map.Entry<String, Integer> entityInfoIsland : infoEntityIsland().entrySet()) {
            String type = entityInfoIsland.getKey();
            int quntity = entityInfoIsland.getValue();

            System.out.println(EMOJI.get(type) + ": " + quntity);
        }

        System.out.println();
        System.out.println("Island: ");

        // Island print
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                cm = island.getCell(i, j).getManager();
                String entitysCell = cm.toString();
                if (j < SIZE_Y - 1) {
                    entitysCell += " }|{ ";
                }

                System.out.print(entitysCell);
            }
            System.out.println();
        }
    }

    // Info Entitys in the Island
    private Map<String, Integer> infoEntityIsland () {
        Map<String, Integer> infoEntityIsland = new HashMap<>();
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                cm = island.getCell(i, j).getManager();
                Map<String, Integer> infoEntityCell = cm.getInfo();

                for (Map.Entry<String, Integer> entityCell : infoEntityCell.entrySet()) {
                    String type = entityCell.getKey();
                    int quntity = entityCell.getValue();

                    if (infoEntityIsland.containsKey(type)) {
                        infoEntityIsland.put(type, infoEntityIsland.get(type) + quntity);
                    } else {
                        infoEntityIsland.put(type, quntity);
                    }
                }
            }
        }

        return infoEntityIsland;
    }
}

