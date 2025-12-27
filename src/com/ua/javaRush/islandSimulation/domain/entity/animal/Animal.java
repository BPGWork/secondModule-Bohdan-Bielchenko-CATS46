package com.ua.javaRush.islandSimulation.domain.entity.animal;

import com.ua.javaRush.islandSimulation.domain.entity.Entity;
import com.ua.javaRush.islandSimulation.domain.entity.animal.predators.Fox;
import com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians.Boar;
import com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians.Duck;
import com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians.Mouse;
import com.ua.javaRush.islandSimulation.domain.island.Cell;
import com.ua.javaRush.islandSimulation.domain.island.CellManager;
import com.ua.javaRush.islandSimulation.domain.island.Island;
import com.ua.javaRush.islandSimulation.dto.DietDto;
import com.ua.javaRush.islandSimulation.dto.DietWrapper;
import com.ua.javaRush.islandSimulation.dto.EntityDto;
import com.ua.javaRush.islandSimulation.dto.EntityFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// This class is responsible for the animal
public abstract class Animal extends Entity {
    Random random = new Random();

    private double weight;
    private double hunger;
    private double foodWeightNeeded;
    private double speed;
    private boolean isAlive;

    public Animal (List<String> parameters, Cell cell) {
        super(parameters, cell);
        weight = numberParameter(0);
        hunger = random.nextDouble(29, 100);
        foodWeightNeeded = numberParameter(1);
        speed = numberParameter(2);
        life();
    }

    // Setter
    public void setHunger (double hunger) {
        this.hunger = hunger;
    }

    // Getters
    public double getWeight () {
        return weight;
    }
    public double getHunger () {
        return hunger;
    }
    public double getFoodWeightNeeded () {
        return foodWeightNeeded;
    }
    public double getSpeed () {
        return speed;
    }
    public boolean getALive () {
        return isAlive;
    }

    // Edit Hungry at tick
    public double hungrySet () {
        double needed = foodWeightNeeded / 4;
        double percent = (needed * 100) / foodWeightNeeded;
        hunger -= percent;
        if (hunger < 0 ) {
            hunger = 0;
        }

        return hunger;
    }

    // Abstract method for eliminating hunger
    public void eat() {
        if (this instanceof Predator) {
            ((Predator) this).eat();
        } else if (this instanceof Vegetarian) {
            if (this instanceof Duck || this instanceof Boar
                    || this instanceof Mouse || this instanceof Fox) {
                if (getCell().getManager().checkEntity("Caterpillar")) {
                    Animal target = (Animal) getCell().getManager().getEntity("Caterpillar");
                    kill(target);
                }
            } else {
                ((Vegetarian) this).eat();
            }
        }
    }

    // Method of moving around the island
    public Cell move() {
        int X = getCell().getX();
        int Y = getCell().getY();

        Cell currentCell = getCell();
        Cell targetCell = currentCell;

        CellManager cellManager;

        for (int i = 0; i < getSpeed(); i++) {
            List<Cell> possibleCell = new ArrayList<>();

            int[][] directions = {
                    {1, 0}, //bottom
                    {-1, 0}, // top
                    {0, 1}, //right
                    {0, -1} // left
            };

            for (int[] d : directions) {
                int nx = X + d[0];
                int ny = Y + d[1];

                if (nx < 0 || ny < 0 || nx >= Island.getSizeX() || ny >= Island.getSizeY()) {
                    continue;
                }

                Cell testCell = Island.getCell(nx, ny);
                cellManager = new CellManager(testCell);
                if (cellManager.checkQuantity(getType()) < numberParameter(3)) {
                    possibleCell.add(testCell);
                }
            }

            if (possibleCell.isEmpty()) {
                break;
            }

            Cell chosen = possibleCell.get(random.nextInt(possibleCell.size()));
            X = chosen.getX();
            Y = chosen.getY();
            targetCell = chosen;
        }

        return targetCell;
    }

    // Reproduction method
    public Entity reproduct() {
        return EntityFactory.createObject(new EntityDto(getType(), getParameters()), getCell());
    }

    // The animal is alive
    public void life () {
        isAlive = true;
    }
    // The animal died
    public void death () {
        isAlive = false;
    }

    public void kill (Animal target) {
        DietWrapper dietWrapper = DietDto.createSpeciesMap();
        int probability = dietWrapper.getProbability(getType(), target.getType());

        if (probability <= 0) {
            return;
        }

        int attempt = random.nextInt(100);
        if (attempt > probability) {
            return;
        }

        double targetWeight = target.getWeight();
        double gainedSatiety = (targetWeight * 100) / this.getFoodWeightNeeded();

        double newSatiety = this.getHunger() + gainedSatiety;
        if (newSatiety > 100) {
            newSatiety = 100;
        }
        this.setHunger(newSatiety);

        target.death();
    }
}
