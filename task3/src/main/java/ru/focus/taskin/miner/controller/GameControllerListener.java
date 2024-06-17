package ru.focus.taskin.miner.controller;

import ru.focus.taskin.miner.model.game.Board;
import ru.focus.taskin.miner.model.game.Cell;
import ru.focus.taskin.miner.model.game.GameProcessListener;
import ru.focus.taskin.miner.model.records.RecordsWriter;
import ru.focus.taskin.miner.view.GameType;

public interface GameControllerListener {
    void setCurrGameType(GameType currGameType);
    void setGameProcessListener(GameProcessListener gameProcessListener);
    void startNewGame();
    void revealCell(Cell currCell);
    void tryOpenNearCells(Cell currCell);
    void toggleFlag(Cell currCell);
    RecordsWriter getRecordsWriter();
    Board getBoard();
    GameType getCurrGameType();
}
