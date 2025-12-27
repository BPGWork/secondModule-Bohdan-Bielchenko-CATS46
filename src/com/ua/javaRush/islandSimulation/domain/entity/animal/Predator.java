package com.ua.javaRush.islandSimulation.domain.entity.animal;

import com.ua.javaRush.islandSimulation.domain.entity.Entity;
import com.ua.javaRush.islandSimulation.domain.island.Cell;
import com.ua.javaRush.islandSimulation.domain.island.CellManager;
import com.ua.javaRush.islandSimulation.dto.DietDto;
import com.ua.javaRush.islandSimulation.dto.DietWrapper;

import java.util.List;

// This class is responsible for implementing the predator
public class Predator extends Animal {
    public Predator (List<String> parameters, Cell cell) {
        super(parameters, cell);
    }

    /* This method is responsible for
       implementing the elimination of the predator's hunger */
    public void eat () {
        List<Entity> entityList = new CellManager(getCell()).snapshot();

        List<Animal> targetAnimal = entityList.stream()
                .filter(entity -> entity instanceof Animal)
                .filter(entity -> entity != this)
                .map(entity -> (Animal) entity)
                .toList();

        if (targetAnimal.isEmpty()) {
            return;
        }

        Animal target = targetAnimal.get(random.nextInt(targetAnimal.size()));
        kill(target);
    }
}
