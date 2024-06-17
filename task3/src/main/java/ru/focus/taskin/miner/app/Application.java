package ru.focus.taskin.miner.app;

import ru.focus.taskin.miner.controller.GameController;
import ru.focus.taskin.miner.model.game.GameProcess;
import ru.focus.taskin.miner.view.*;

public class Application {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
        GameProcess gameModel = new GameProcess();
        GameController gameController = new GameController(mainWindow, gameModel);
        settingsWindow.setGameTypeListener(gameController);
        gameController.startGame();

        mainWindow.setNewGameMenuAction(e -> gameController.startGame());
        mainWindow.setSettingsMenuAction(e -> gameController.openSettingsWindow(settingsWindow));
        mainWindow.setHighScoresMenuAction(e -> gameController.openHighScoresWindow(highScoresWindow));
        mainWindow.setExitMenuAction(e -> mainWindow.dispose());
        mainWindow.setCellListener(gameController);

        mainWindow.setVisible(true);
    }
}
