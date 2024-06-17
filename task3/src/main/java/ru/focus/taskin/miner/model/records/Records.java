package ru.focus.taskin.miner.model.records;

import ru.focus.taskin.miner.view.GameType;

public class Records {
    private GameType gameType;
    private int winTime;
    private String name;

    public Records(GameType gameType, int winTime) {
        this.gameType = gameType;
        this.winTime = winTime;
    }

    public Records() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getWinTime() {
        return winTime;
    }
}
