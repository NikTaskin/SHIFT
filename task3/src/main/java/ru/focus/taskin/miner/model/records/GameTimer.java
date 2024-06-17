package ru.focus.taskin.miner.model.records;

import ru.focus.taskin.miner.model.game.GameProcessListener;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private static final int DELAY = 0;
    private static final int SCHEDULE_PERIOD_IN_MILLISECONDS = 1000;
    private Timer timer;
    private GameProcessListener gameProcessListener;

    private long startTime;
    private int currentTimerSeconds;

    public int getCurrentTimerSeconds() {
        return currentTimerSeconds;
    }

    public void startTimer() {
        timer = new Timer();
        startTime = new Date().getTime();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                currentTimerSeconds = (int) (new Date().getTime() - startTime) / 1000;
                gameProcessListener.onSetTimerValue(currentTimerSeconds);
            }
        };
        timer.schedule(timerTask, DELAY, SCHEDULE_PERIOD_IN_MILLISECONDS);

    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void resetTimer() {
        stopTimer();
        currentTimerSeconds = 0;
        gameProcessListener.onSetTimerValue(0);
    }

    public void setGameInteractionListener(GameProcessListener gameProcessListener) {
        this.gameProcessListener = gameProcessListener;
    }
}
