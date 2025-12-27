package com.ua.javaRush.islandSimulation.domain.entity;

import com.ua.javaRush.islandSimulation.domain.island.Cell;
import com.ua.javaRush.islandSimulation.domain.island.CellManager;

import java.util.List;

// This class is responsible for entities
public abstract class Entity {
    private String type;
    private List<String> parameters;
    private static int idEntity = 0;
    private volatile Cell cell;

    public Entity (List<String> parameters, Cell cell) {
        this.parameters = parameters;
        ++idEntity;
        this.cell = cell;
    }

    // This method preserves the type of dryness
    public void setType (String type) {
        this.type = type;
    }
    public void setCell (Cell cell) {
        this.cell = cell;
    }

    // This method returns type
    public String getType () {
        return type;
    }
    public List<String> getParameters () {
        return parameters;
    }
    // This method returns the unique ID of the object
    public int getIdEntity () {
        return idEntity;
    }
    // This method returns the coordinates of the location
    public Cell getCell () {
        return cell;
    }

    // This method edits the parameter, taking only the number
    public Double numberParameter (int index) {
        return Double.parseDouble(parameters.get(index).
                substring(parameters.get(index).indexOf(":") + 1, parameters.get(index).length()));
    }

    @Override
    public String toString () {
        return type +
                " {" + parameters + "}";
    }
}
