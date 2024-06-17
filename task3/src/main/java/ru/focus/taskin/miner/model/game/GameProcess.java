package ru.focus.taskin.miner.model.game;

import ru.focus.taskin.miner.config.AppConfig;
import ru.focus.taskin.miner.controller.GameControllerListener;
import ru.focus.taskin.miner.model.records.GameTimer;
import ru.focus.taskin.miner.model.records.RecordsWriter;
import ru.focus.taskin.miner.view.GameImage;
import ru.focus.taskin.miner.view.GameType;

import java.util.*;

public class GameProcess implements GameControllerListener {
    public static final GameType DEFAULT_GAME_TYPE = GameType.NOVICE;
    private GameType currGameType;
    private Board board;
    private GameProcessListener gameProcessListener;
    private final GameTimer gameTimer = new GameTimer();
    private boolean firstMove;
    private int countOfFlags;
    private final AppConfig appConfig = new AppConfig();

    public final RecordsWriter recordsWriter = new RecordsWriter(appConfig.getRecordFilePath());

    @Override
    public void setGameProcessListener(GameProcessListener gameProcessListener) {
        this.gameProcessListener = gameProcessListener;
        gameTimer.setGameInteractionListener(gameProcessListener);
    }

    @Override
    public void setCurrGameType(GameType currGameType) {
        this.currGameType = currGameType;
    }

    @Override
    public GameType getCurrGameType() {
        return currGameType;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public RecordsWriter getRecordsWriter() {
        return recordsWriter;
    }

    public GameProcess() {
        this.board = new Board(DEFAULT_GAME_TYPE.getWidth(), DEFAULT_GAME_TYPE.getHeight(), DEFAULT_GAME_TYPE.getMineCount());
    }

    @Override
    public void startNewGame() {
        board = new Board(currGameType.getWidth(), currGameType.getHeight(), currGameType.getMineCount());
        gameTimer.resetTimer();
        countOfFlags = 0;
        firstMove = true;
        currGameType = getCurrGameType();
        gameProcessListener.onSetNumbersOfMine(board.getNumMines());
    }

    @Override
    public void revealCell(Cell currCell) {
        if (firstMove) {
            board.initMines(currCell);
            board.initNeighbors();
            gameTimer.startTimer();
            firstMove = false;
        }
        if (currCell.isFlagged() || currCell.isOpen()) {
            return;
        }
        currCell.openCell();
        if (currCell.isMine()) {
            setCellsImage(currCell, GameImage.BOMB);
            openAllMines();
            gameTimer.stopTimer();
            gameProcessListener.onLose();
        } else {
            switch (currCell.getNumNearMines()) {
                case (0) -> {
                    setCellsImage(currCell, GameImage.EMPTY);
                    openNearEmptyCells(currCell);
                }
                case (1) -> setCellsImage(currCell, GameImage.NUM_1);
                case (2) -> setCellsImage(currCell, GameImage.NUM_2);
                case (3) -> setCellsImage(currCell, GameImage.NUM_3);
                case (4) -> setCellsImage(currCell, GameImage.NUM_4);
                case (5) -> setCellsImage(currCell, GameImage.NUM_5);
                case (6) -> setCellsImage(currCell, GameImage.NUM_6);
                case (7) -> setCellsImage(currCell, GameImage.NUM_7);
                case (8) -> setCellsImage(currCell, GameImage.NUM_8);
                default -> setCellsImage(currCell, GameImage.CLOSED);

            }
            if (checkWin()) {
                gameTimer.stopTimer();
                openAllMines();
                recordsWriter.checkRecord(currGameType, gameTimer.getCurrentTimerSeconds());
                gameProcessListener.onWin();
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                Cell cell = board.getCell(i, j);
                if (!cell.isOpen() && !cell.isMine()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void tryOpenNearCells(Cell currCell) {
        if (!currCell.isOpen()) {
            return;
        }
        int numNearMines = currCell.getNumNearMines();
        int numFlaggedNeighbors = 0;

        for (Cell neighbor : currCell.getNeighbors()) {
            if (neighbor.isFlagged()) {
                numFlaggedNeighbors++;
            }
        }

        if (numFlaggedNeighbors == numNearMines) {
            for (Cell neighbor : currCell.getNeighbors()) {
                if (!neighbor.isFlagged() && !neighbor.isOpen()) {
                    revealCell(neighbor);
                }
            }
        }
    }

    private void openNearEmptyCells(Cell startCell) {
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();
        queue.add(startCell);
        while (!queue.isEmpty()) {
            Cell currCell = queue.poll();
            List<Cell> neighbors = currCell.getNeighbors();
            for (Cell neighbor : neighbors) {
                if (!neighbor.isMine() && !neighbor.isOpen() && !visited.contains(neighbor)) {
                    revealCell(neighbor);
                    visited.add(neighbor);
                    if (neighbor.getNumNearMines() == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    private void openAllMines() {
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                if (board.getCell(i, j).isMine()) {
                    setCellsImage(board.getCell(i, j), GameImage.BOMB);
                }
            }
        }
    }

    @Override
    public void toggleFlag(Cell currCell) {
        if (!currCell.isOpen() && !currCell.isFlagged() && countOfFlags != board.getNumMines()) {
            board.toggleFlag(currCell.getX(), currCell.getY());
            setCellsImage(currCell, GameImage.MARKED);
            countOfFlags++;
            gameProcessListener.onSetNumbersOfMine(board.getNumMines() - countOfFlags);
            return;
        }
        if (currCell.isFlagged()) {
            board.toggleFlag(currCell.getX(), currCell.getY());
            countOfFlags--;
            gameProcessListener.onSetNumbersOfMine(board.getNumMines() - countOfFlags);
            setCellsImage(currCell, GameImage.CLOSED);
        }
    }

    private void setCellsImage(Cell currCell, GameImage gameImage) {
        gameProcessListener.onSetCellImage(currCell, gameImage);
    }
}
