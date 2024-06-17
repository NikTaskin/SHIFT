package ru.focus.taskin.logger;

import org.apache.logging.log4j.LogManager;

public final class MainLogger {
    MainLogger(){
    }
    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("MainLogger");
}
