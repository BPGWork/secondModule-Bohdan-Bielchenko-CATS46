package com.ua.javaRush.islandSimulation.dto;

import com.ua.javaRush.islandSimulation.domain.entity.ActionType;
import com.ua.javaRush.islandSimulation.domain.entity.Entity;
import com.ua.javaRush.islandSimulation.domain.entity.animal.Animal;
import com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians.Caterpillar;

import java.util.Random;

// This class is responsible for the entity's action
public class ActionDto {
    Random random = new Random();

    private Entity entity;
    private int idEntity;
    private ActionType action;

    public ActionDto (Entity entity) {
        this.entity = entity;
        idEntity = entity.getIdEntity();

        // Choose Action on this tick
        if (entity instanceof Animal) {
            if (((Animal) entity).getHunger() <= 30) {
                action = ActionType.EAT;
            } else if (entity instanceof Caterpillar) {
                action = ActionType.values()[
                        random.nextInt(1, ActionType.values().length - 2)];
            } else {
                action = ActionType.values()[
                        random.nextInt(1, ActionType.values().length - 1)];
            }
        } else {
            action = ActionType.GROW;
        }
    }

    // Getters
    public Entity getEntity () {
        return entity;
    }
    public ActionType getAction () {
        return action;
    }
}
