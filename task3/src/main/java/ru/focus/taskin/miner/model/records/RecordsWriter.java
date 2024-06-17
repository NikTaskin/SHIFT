package ru.focus.taskin.miner.model.records;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.focus.taskin.miner.view.GameType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.focus.taskin.miner.model.logger.RecordLogger.logger;

public class RecordsWriter {
    private final File recordFile;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private RecordListener recordListener;
    private Records pendingToSaveRecord;

    public RecordsWriter(String recordFilePath) {
        this.recordFile = new File(recordFilePath);
    }

    public List<Records> getAllRecords() {
        List<Records> records = new ArrayList<>();
        try {
            if (recordFile.exists()) {
                records = objectMapper.readValue(recordFile, new TypeReference<>() {});
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return records;
    }

    public void checkRecord(GameType gameType, int timeRecord) {
        var records = getAllRecords();
        Records currentLevelRecord = records.stream().filter(x -> x.getGameType() == gameType).findFirst().orElse(null);

        var isRecord = currentLevelRecord == null || currentLevelRecord.getWinTime() > timeRecord;

        if (isRecord) {
            pendingToSaveRecord = new Records(gameType, timeRecord);
            setName();
        }
    }

    public void writeRecord(String name) {
        if (pendingToSaveRecord != null) {
            pendingToSaveRecord.setName(name);
            try {
                var records = getAllRecords();
                records.removeIf(x -> x.getGameType() == pendingToSaveRecord.getGameType());
                records.add(pendingToSaveRecord);
                objectMapper.writeValue(recordFile, records);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void setListener(RecordListener recordListener) {
        this.recordListener = recordListener;
    }

    private void setName() {
        if (recordListener != null) {
            recordListener.setName();
        }
    }
}
