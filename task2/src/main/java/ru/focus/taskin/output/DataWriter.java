package ru.focus.taskin.output;

import ru.focus.taskin.exceptions.NullOutputDataException;

import java.io.*;

import static ru.focus.taskin.logger.MainLogger.logger;

public final class DataWriter {
    DataWriter() {
    }

    private static void write(PrintWriter printWriter, String outputData) throws NullOutputDataException {
        if (outputData == null) {
            throw new NullOutputDataException("Ошибка формирования выходной информации");
        }
        printWriter.write(outputData);
        printWriter.flush();
    }

    public static void writeToFile(String outputData, String outputFile) {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            write(writer, outputData);
        } catch (FileNotFoundException e) {
            logger.error("Не удалось записать в файл", e);
        } catch (NullOutputDataException e) {
            logger.error(e.getMessage());
        }
    }

    public static void writeToConsole(String outputData) throws NullOutputDataException {
        write(new PrintWriter(System.out), outputData);
    }
}