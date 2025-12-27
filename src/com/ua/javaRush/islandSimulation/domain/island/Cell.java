package com.ua.javaRush.islandSimulation.domain.island;

import com.ua.javaRush.islandSimulation.domain.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// This class is responsible for the coordinates of the entity
public class Cell {
    private int x;
    private int y;

    private CellManager manager;

    protected List<Entity> entityList = new ArrayList<>();
    protected Lock lock = new ReentrantLock();

    public Cell (int x, int y) {
        this.x = x;
        this.y = y;

        manager = new CellManager(this);
    }

    public int getX () {
        return x;
    }
    public int getY () {
        return y;
    }
    public CellManager getManager () {
        return manager;
    }

    public boolean equals (Object obj) {
        if (obj == null) {
            return false;
        }

        Cell cell = (Cell) obj;

        if (x != cell.getX() || y != cell.getY()) {
            return false;
        }

        return true;
    }
}
