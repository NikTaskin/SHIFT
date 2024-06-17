package ru.focus.taskin.miner.model.game;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int x;
    private int y;
    private boolean isMine;
    private boolean isOpen;
    private int numNearMines;
    private boolean isFlagged;
    private final List<Cell> neighbors = new ArrayList<>();

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setNeighbors(Cell currCell) {
        neighbors.add(currCell);
    }

    public List<Cell> getNeighbors() {
        return neighbors;
    }

    public void setMine() {
        isMine = true;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setNumNearMines(int numNearMines) {
        this.numNearMines = numNearMines;
    }

    public int getNumNearMines() {
        return numNearMines;
    }

    public void openCell() {
        if (!isOpen && !isFlagged) {
            isOpen = true;
        }
    }

    public void onOffFlag() {
        if (!isOpen) {
            isFlagged = !isFlagged;
        }
    }
}
