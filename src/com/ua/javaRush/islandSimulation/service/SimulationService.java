package com.ua.javaRush.islandSimulation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.javaRush.islandSimulation.domain.entity.Entity;
import com.ua.javaRush.islandSimulation.domain.island.Cell;
import com.ua.javaRush.islandSimulation.domain.island.CellManager;
import com.ua.javaRush.islandSimulation.domain.island.Island;
import com.ua.javaRush.islandSimulation.dto.EntityDto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

public class SimulationService {
    private Random random = new Random();

    private final List<EntityDto> entityList = new EntityDto().addParemeters().entityList;

    private static final String RESOURCE_PATH = "simulationConfig.json";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Island island;
    public static IslandTickPrinter printer;

    public SimulationService () {
        island = createIsland();
        printer = new IslandTickPrinter(island);
    }

    private Island createIsland () {
        Map<String, Integer> islandParameters;
        ObjectMapper mapper = new ObjectMapper();
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (InputStream is = SimulationService.class
                .getClassLoader()
                .getResourceAsStream(RESOURCE_PATH)) {

            if (is == null) {
                throw new IllegalStateException(
                        "simulationConfig.json not found in classpath: " + RESOURCE_PATH
                );
            }

            islandParameters = MAPPER.readValue(
                    is,
                    new TypeReference<Map<String, Integer>>() {}
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Cannot load simulation config from classpath: " + RESOURCE_PATH, e
            );
        }

        island = new Island(islandParameters.get("SIZE_X"), islandParameters.get("SIZE_Y"));

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < Island.getSizeX(); i++) {
            for (int j = 0; j < Island.getSizeY(); j++) {

                final int fx = i;
                final int fy = j;

                tasks.add(() -> {
                    Cell cell = Island.getCell(fx, fy);
                    CellManager cm = cell.getManager();

                    List<EntityDto> snapshotEntityList = new ArrayList<>(entityList);
                    Map<EntityDto, Integer> entitysForCell = new HashMap<>();

                    entitysForCell.put(snapshotEntityList.get(15), 1);
                    snapshotEntityList.remove(15);
                    for (int k = 1; k < islandParameters.get("NumberOfEntityTypesPerCell"); k++) {
                        int indexEntity = random.nextInt(snapshotEntityList.size());
                        EntityDto entityDto = snapshotEntityList.remove(indexEntity);
                        entitysForCell.put(entityDto, random.nextInt(1,
                                numberParameter(entityDto.parameters, 3) + 1));
                    }

                    for (Map.Entry<EntityDto, Integer> entryEntity : entitysForCell.entrySet()) {
                        EntityDto entityDto = entryEntity.getKey();
                        int quantity = entryEntity.getValue();

                        for (int k = 0; k < quantity; k++) {
                            Entity entity = cm.createEntity(entityDto);
                            cm.addEntity(entity);
                        }
                    }

                    return null;
                });
            }
        }

        try {
            List<Future<Void>> futures = pool.invokeAll(tasks);
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(
                            "Task failed with exception",
                            e.getCause()
                    );
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Population interrupted", e);
        } finally {
            pool.shutdown();
        }

        return island;
    }

    public void tick(int tickNumber) {
        int sizeX = Island.getSizeX();
        int sizeY = Island.getSizeY();

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {

                final int fx = x;
                final int fy = y;

                tasks.add(() -> {
                    Cell cell = Island.getCell(fx, fy);
                    CellManager cm = cell.getManager();
                    cm.tick();


                    return null;
                });
            }
        }
        try {
            List<Future<Void>> futures = pool.invokeAll(tasks);
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(
                            "Task failed with exception",
                            e.getCause()
                    );
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Tick interrupted", e);
        } finally {
            pool.shutdown();
        }

        printer.print("" + tickNumber);
    }

    // This method edits the parameter, taking only the number
    public int numberParameter (List<String> parameters, int index) {
        return Integer.parseInt(parameters.get(index).
                substring(parameters.get(index).indexOf(":") + 1, parameters.get(index).length()));
    }
}
