package com.ua.javaRush.islandSimulation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Parameter wrapper
public class EntityWrapper {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public List<EntityDto> entityList;
}
