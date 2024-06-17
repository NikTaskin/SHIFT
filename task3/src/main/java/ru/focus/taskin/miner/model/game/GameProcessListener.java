package ru.focus.taskin.miner.model.game;

import ru.focus.taskin.miner.view.GameImage;

public interface GameProcessListener {
    void onSetCellImage(Cell cell, GameImage image);
    void onLose();
    void onWin();
    void onSetNumbersOfMine(int mineCount);
    void onSetTimerValue(int timeSeconds);
}
