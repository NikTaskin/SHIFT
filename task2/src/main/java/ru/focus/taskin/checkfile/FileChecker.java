package ru.focus.taskin.checkfile;

import ru.focus.taskin.exceptions.*;

import java.io.*;

public final class FileChecker {
    private FileChecker() {
    }
    static final String FILE_FORMAT = ".txt";

    public static void checkInputFile(File file) throws GeneralApplicationException {
        if (!isFileValid(file)) {
            throw new InvalidFileException("Файл не в формате .txt");
        }
        if (isFileEmpty(file)) {
            throw new EmptyFileException(String.format("Файл %s пустой", file.getName()));
        }
    }

    private static boolean isFileValid(File file) {
        return file.isFile() && file.getName().toLowerCase().endsWith(FILE_FORMAT);
    }

    private static boolean isFileEmpty(File file) {
        return file.length() == 0;
    }
}