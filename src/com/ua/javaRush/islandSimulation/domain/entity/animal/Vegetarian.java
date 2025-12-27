package com.ua.javaRush.islandSimulation.domain.entity.animal;

import com.ua.javaRush.islandSimulation.domain.entity.plant.Plant;
import com.ua.javaRush.islandSimulation.domain.island.Cell;
import com.ua.javaRush.islandSimulation.domain.island.CellManager;
import com.ua.javaRush.islandSimulation.domain.island.Island;

import java.util.List;
import java.util.Random;

// This class is responsible for implementing herbivory
public class Vegetarian extends Animal {
    Random random = new Random();

    public Vegetarian (List<String> parameters, Cell cell) {
        super(parameters, cell);
    }

    /* This method is responsible for
       implementing the elimination of herbivore hunger */
    public void eat () {
        Plant plant = (Plant) Island.getCell(getCell().getX(), getCell().getY()).getManager().getEntity("Plant");
        if (plant == null) {
            return;
        }

        double plantCapacity = plant.getCapacity();
        if (plantCapacity <= 0) {
            return;
        }

        if (getHunger() >= 100) {
            return;
        }

        double neededKg = (getHunger() * getFoodWeightNeeded()) / 100.0;
        if (neededKg <= 0) {
            return;
        }

        double ate = 0;

        if (plantCapacity <= neededKg) {
            ate = plant.getCapacity() - 1;
            plant.setCapacity(0);
        } else if (plant.getCapacity() > neededKg) {
            double minEat = neededKg * 0.03;

            if (minEat < 0.001) {
                minEat = 0.001;
            }
            if (minEat > neededKg) {
                minEat = neededKg;
            }

            ate = minEat + (neededKg - minEat) * random.nextDouble();

            if (ate > plantCapacity) {
                ate = plantCapacity;
            }

            plant.setCapacity(plant.getCapacity() - ate);
        }

        double gainedPercent = (ate * 100.0) / getFoodWeightNeeded();
        double newSatiety = getHunger() + gainedPercent;
        if (newSatiety > 100) {
            newSatiety = 100;
        }
        setHunger(newSatiety);
    }
}
