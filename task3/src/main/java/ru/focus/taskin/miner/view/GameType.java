package ru.focus.taskin.miner.view;

public enum GameType {
    NOVICE(9, 9, 10),
    MEDIUM(16, 16, 40),
    EXPERT(30, 16, 99);

    private final int width;
    private final int height;
    private final int mineCount;

    GameType(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMineCount() {
        return mineCount;
    }
}
