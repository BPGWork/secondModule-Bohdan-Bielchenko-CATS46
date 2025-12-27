package com.ua.javaRush.islandSimulation.domain.island;

import com.ua.javaRush.islandSimulation.domain.entity.ActionType;
import com.ua.javaRush.islandSimulation.domain.entity.Entity;
import com.ua.javaRush.islandSimulation.domain.entity.animal.Animal;
import com.ua.javaRush.islandSimulation.domain.entity.Emoji;
import com.ua.javaRush.islandSimulation.domain.entity.animal.Predator;
import com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians.Boar;
import com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians.Duck;
import com.ua.javaRush.islandSimulation.domain.entity.animal.vegetarians.Mouse;
import com.ua.javaRush.islandSimulation.domain.entity.plant.Plant;
import com.ua.javaRush.islandSimulation.dto.ActionDto;
import com.ua.javaRush.islandSimulation.dto.EntityDto;
import com.ua.javaRush.islandSimulation.dto.EntityFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class CellManager {
    private Cell cell;
    private List<ActionDto> actionList;
    private List<Callable<Void>> task;

    public CellManager (Cell cell) {
        this.cell = cell;
    }

    // Starting Cell Manager
    public void tick() throws InterruptedException {
        removeDeadEntities();
        setActionList();
        completionTask();
        removeDeadEntities();
    }

    // Create Entity
    public Entity createEntity (EntityDto entityDto) {
        return EntityFactory.createObject(entityDto, cell);
    }

    // Adds an entity to the List
    public void addEntity (Entity entity) {
        cell.lock.lock();

        try {
            cell.entityList.add(entity);
        } finally {
            cell.lock.unlock();
        }
    }

    // Checks the number of entities by type in the List
    public int checkQuantity (String type) {
        cell.lock.lock();

        try {
            int quantity = 0;

            for (Entity entity : cell.entityList) {
                if (type.equals("Plant") && entity instanceof Plant) {
                    return (int) ((Plant) entity).getCapacity();
                }

                if (type.equals(entity.getType())) {
                    quantity++;
                }
            }

            return quantity;
        } finally {
            cell.lock.unlock();
        }


    }

    /* Returns the entity by type (
       made exclusively for plants) */
    public Entity getEntity (String type) {
        cell.lock.lock();

        try {
            for (Entity entity : cell.entityList) {
                if (type.equals(entity.getType())) {
                    return entity;
                }
            }
        } finally {
            cell.lock.unlock();
        }

        return null;
    }

    // Removes an entity from the List
    public boolean checkEntity (String type) {
        cell.lock.lock();

        try {
            return cell.entityList.contains(getEntity("Caterpillar"));
        } finally {
            cell.lock.unlock();
        }
    }

    // Remove dead Entity
    public void removeDeadEntities () {
        cell.lock.lock();
        try {
            cell.entityList.removeIf(e -> (e instanceof Animal a) && a.getALive() == false);
        } finally {
            cell.lock.unlock();
        }
    }

    // Return a copy of the Entity List
    public List<Entity> snapshot () {
        cell.lock.lock();

        try {
            return new ArrayList<>(cell.entityList);
        } finally {
            cell.lock.unlock();
        }
    }

    // Saves the list of tasks
    public void setActionList() {
        cell.lock.lock();

        try {
            actionList = new ArrayList<>();
            for (Entity entity : cell.entityList) {
                actionList.add(new ActionDto(entity));
            }
        } finally {
            cell.lock.unlock();
        }
    }

    // Launches tasks
    public void completionTask () throws InterruptedException {
        task = new ArrayList<>();
        List<ActionDto> localActions;
        cell.lock.lock();

        try {
            localActions = new ArrayList<>(actionList);
        } finally {
            cell.lock.unlock();
        }

        for (ActionDto actionTask : localActions) {
            Entity entity = actionTask.getEntity();

            if (entity instanceof Animal a && a.getHunger() < 15) {
                ((Animal) entity).death();
            }

            if (entity instanceof Animal a && a.getALive() == false) {
                continue;
            }

            int quantity;
            cell.lock.lock();
            try {
                if (!cell.entityList.contains(entity)) {
                    continue;
                }
                quantity = 0;
                for (Entity e : cell.entityList) {
                    if (entity.getType().equals(e.getType())) quantity++;
                }
            } finally {
                cell.lock.unlock();
            }

            ActionType action = actionTask.getAction();

            switch (action) {
                case EAT -> ((Animal) entity).eat();
                case REPRODUCT -> reproductInspection(entity, action, quantity);
                case MOVE -> moveInspection(entity);
                case GROW -> ((Plant) entity).grow();
            }

            if (action != ActionType.EAT && action != ActionType.GROW) {
                ((Animal) entity).setHunger(((Animal) entity).hungrySet());

            }

        }
    }

    // Method for post-breeding inspection
    void reproductInspection (Entity entity, ActionType action, int quantity) {
        if (quantity < entity.numberParameter(3)) {
            if (quantity >= 2) {
                addEntity(((Animal) entity).reproduct());
            }
        } else {
            return;
        }
    }

    // Method for post-transportation inspection
    public void moveInspection (Entity entity) {
        if (!(entity instanceof Animal)) {
            return;
        }

        Cell to = ((Animal) entity).move();
        if (to == null || cell.equals(to)) {
            return;
        }

        Cell from = this.cell;
        Cell target = Island.getCell(to.getX(), to.getY());

        Cell first = firstByCoord(from, target);
        Cell second = (first == from) ? target : from;

        first.lock.lock();
        second.lock.lock();
        try {
            if (!from.entityList.contains(entity)) return;

            int limit = entity.numberParameter(3).intValue();
            int count = 0;
            for (Entity e : target.entityList) {
                if (entity.getType().equals(e.getType())) {
                    count++;
                }
            }
            if (count >= limit) {
                return;
            }


            from.entityList.remove(entity);
            target.entityList.add(entity);
            entity.setCell(target);

        } finally {
            second.lock.unlock();
            first.lock.unlock();
        }
    }

    private Cell firstByCoord(Cell a, Cell b) {
        if (a.getX() < b.getX()) return a;
        if (a.getX() > b.getX()) return b;
        if (a.getY() <= b.getY()) return a;
        return b;
    }


    // Get information this cell
    public Map<String, Integer> getInfo () {
        List<Entity> entityList = snapshot();

        Map<String, Integer> infoEntity = new HashMap<>();

        for (int i = 0; i < entityList.size(); i++) {
            String type = entityList.get(i).getType();

            if (infoEntity.containsKey(type)) {
                continue;
            }

            int quantity = checkQuantity(type);
            infoEntity.put(type, quantity);
        }

        return infoEntity;
    }

    // print information this cell
    public String toString () {
        final Map<String, String> EMOJI = Emoji.getEMOJI();

        String printEntity =  "(" + cell.getX() + ", " + cell.getY() + "): [";

        for (Map.Entry<String, Integer> entryInfo : getInfo().entrySet()) {
            String type = entryInfo.getKey();
            int quantity = entryInfo.getValue();
            String entitys = EMOJI.get(type) + ": " + quantity;

            if (entitys.length() < 8) {
                for (int i = entitys.length(); i < 8; i++) {
                    entitys += " ";
                }
            }

            printEntity += entitys + ", ";
        }

        return printEntity.substring(0, printEntity.lastIndexOf(',')) + "]";
    }
}
