package ru.focus.taskin.output;

import ru.focus.taskin.exceptions.GeneralApplicationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.focus.taskin.checkfile.FileChecker.checkInputFile;
import static ru.focus.taskin.logger.MainLogger.logger;

public final class DataReader {
    private DataReader(){}
    public static List<String> readFromFile(File file) throws GeneralApplicationException {
        List<String> inputData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checkInputFile(file);
            String line;
            while ((line = reader.readLine()) != null) {
                inputData.add(line);
            }
        } catch (IOException e) {
            logger.error("Проблемы с чтением файла {}", e.getMessage());
        } catch (NullPointerException e) {
            logger.error("Строк в файле меньше двух");
        }
        return inputData;
    }
}
