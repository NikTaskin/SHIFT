package ru.focus.taskin.miner.model.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecordLogger {
    RecordLogger() {
    }

    public static final Logger logger = LogManager.getLogger("RecordLogger");
}
