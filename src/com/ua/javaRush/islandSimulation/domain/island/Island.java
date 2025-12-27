package com.ua.javaRush.islandSimulation.domain.island;

// This class is responsible for the island
public class Island {
    private static Cell[][] grid;

    public Island (int SIZE_X, int SIZE_Y) {
        grid = new Cell[SIZE_X][SIZE_Y];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public static int getSizeX () {
        return grid.length;
    }
    public static int getSizeY () {
        return grid[0].length;
    }
    public static Cell getCell (int x, int y) {
        return grid[x][y];
    }
}
