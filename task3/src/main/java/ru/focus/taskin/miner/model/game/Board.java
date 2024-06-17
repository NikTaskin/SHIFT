package ru.focus.taskin.miner.model.game;

import java.util.Random;

public class Board {
    private Cell[][] cells;
    private final int width;
    private final int height;
    private final int numMines;
    private final Random random = new Random();

    public Board(int width, int height, int numMines) {
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        initCells();
    }

    private void initCells() {
        cells = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void initMines(Cell clickedCell) {
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (cells[x][y].isMine() || cells[x][y] == clickedCell) {
                continue;
            }

            cells[x][y].setMine();
            minesPlaced++;
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!cells[i][j].isMine()) {
                    int adjacentMines = countNearMines(i, j);
                    cells[i][j].setNumNearMines(adjacentMines);
                }
            }
        }
    }

    public void initNeighbors() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addNeighbors(cells[i][j]);
            }
        }
    }

    private int countNearMines(int x, int y) {
        int count = 0;
        for (int i = Math.max(0, x - 1); i <= Math.min(width - 1, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(height - 1, y + 1); j++) {
                if (cells[i][j].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    public void toggleFlag(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            cells[x][y].onOffFlag();
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cells[x][y];
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumMines() {
        return numMines;
    }

    private void addNeighbors(Cell currCell) {
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        for (int i = 0; i < dx.length; i++) {
            Cell adj = getCell(currCell.getX() + dx[i], currCell.getY() + dy[i]);
            if (adj != null) {
                currCell.setNeighbors(adj);
            }
        }
    }
}
