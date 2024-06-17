package ru.focus.taskin.miner.controller;

import ru.focus.taskin.miner.model.game.Cell;
import ru.focus.taskin.miner.model.game.GameProcess;
import ru.focus.taskin.miner.model.game.GameProcessListener;
import ru.focus.taskin.miner.model.records.RecordListener;
import ru.focus.taskin.miner.model.records.Records;
import ru.focus.taskin.miner.view.*;

public class GameController implements CellEventListener, GameTypeListener, GameProcessListener, RecordListener, RecordNameListener {

    private final MainWindow mainWindow;
    private final GameControllerListener gameModel;

    public GameController(MainWindow mainWindow, GameControllerListener gameModel) {
        this.mainWindow = mainWindow;
        this.gameModel = gameModel;
        this.gameModel.setGameProcessListener(this);
        this.gameModel.getRecordsWriter().setListener(this);
        this.gameModel.setCurrGameType(GameProcess.DEFAULT_GAME_TYPE);
    }

    @Override
    public void onSetCellImage(Cell currCell, GameImage gameImage) {
        mainWindow.setCellImage(currCell.getX(), currCell.getY(), gameImage);
    }

    @Override
    public void onLose() {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setNewGameListener(e -> startGame());
        loseWindow.setExitListener(e -> mainWindow.dispose());
        loseWindow.setVisible(true);
    }

    @Override
    public void onWin() {
        WinWindow winWindow = new WinWindow(mainWindow);
        winWindow.setNewGameListener(e -> startGame());
        winWindow.setExitListener(e -> mainWindow.dispose());
        winWindow.setVisible(true);
    }

    @Override
    public void setName() {
        RecordsWindow recordsView = new RecordsWindow(mainWindow);
        recordsView.setNameListener(this);
        recordsView.setVisible(true);
    }

    @Override
    public void onRecordNameEntered(String name) {
        gameModel.getRecordsWriter().writeRecord(name);
    }

    @Override
    public void onSetNumbersOfMine(int mineCount) {
        mainWindow.setBombsCount(mineCount);
    }

    @Override
    public void onSetTimerValue(int timeSeconds) {
        mainWindow.setTimerValue(timeSeconds);
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        Cell cell = gameModel.getBoard().getCell(x, y);
        switch (buttonType) {
            case LEFT_BUTTON -> {
                gameModel.revealCell(cell);
                mainWindow.repaint();
            }
            case RIGHT_BUTTON -> {
                gameModel.toggleFlag(cell);
                mainWindow.repaint();
            }
            case MIDDLE_BUTTON -> {
                gameModel.tryOpenNearCells(cell);
                mainWindow.repaint();
            }
        }
    }

    @Override
    public void onGameTypeChanged(GameType gameType) {
        gameModel.setCurrGameType(gameType);
        startGame();
    }

    public void startGame() {
        mainWindow.createGameField(gameModel.getCurrGameType().getHeight(), gameModel.getCurrGameType().getWidth());
        gameModel.startNewGame();
    }

    public void openSettingsWindow(SettingsWindow settingsView) {
        settingsView.setGameType(gameModel.getCurrGameType());
        settingsView.setVisible(true);
    }

    public void openHighScoresWindow(HighScoresWindow highScoresView) {
        var records = gameModel.getRecordsWriter().getAllRecords();
        for (Records currRecord : records) {
            switch (currRecord.getGameType()) {
                case NOVICE -> highScoresView.setNoviceRecord(currRecord.getName(), currRecord.getWinTime());
                case MEDIUM -> highScoresView.setMediumRecord(currRecord.getName(), currRecord.getWinTime());
                case EXPERT -> highScoresView.setExpertRecord(currRecord.getName(), currRecord.getWinTime());
            }
        }
        highScoresView.setVisible(true);
    }
}
