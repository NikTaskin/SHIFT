package ru.focus.taskin;

import org.apache.commons.cli.*;
import ru.focus.taskin.exceptions.GeneralApplicationException;
import ru.focus.taskin.factory.FigureFactory;
import ru.focus.taskin.figure.Figure;
import ru.focus.taskin.figure.FigureType;

import java.io.*;
import java.util.List;

import static ru.focus.taskin.logger.MainLogger.logger;
import static ru.focus.taskin.output.DataReader.readFromFile;
import static ru.focus.taskin.output.DataWriter.writeToConsole;
import static ru.focus.taskin.output.DataWriter.writeToFile;

public class Main {
    private static final String FLAG_F = "f";
    private static final String FLAG_C = "c";
    public static void main(String[] args) {
        try {
            CommandLine cmdLine = parseCmd(args);
            if (cmdLine.getOptions().length + cmdLine.getArgs().length < 2) {
                logger.error("В параметрах командной строки меньше двух аргументов");
                System.exit(0);
            }

            File file = new File(cmdLine.getArgs()[0]);
            List<String> inputData = readFromFile(file);
            FigureType inputFigure = FigureType.valueOf(inputData.get(0));
            Figure figure = new FigureFactory().createFigure(inputFigure, inputData);
            String outputData = figure.calculateInfo();
            if (cmdLine.hasOption(FLAG_F)) {
                writeToFile(outputData, cmdLine.getOptionValue(FLAG_F));
            }
            if (cmdLine.hasOption(FLAG_C)) {
                writeToConsole(outputData);
            }
        } catch (ParseException e) {
            logger.error("Ошибка парсинга командной строки", e);
        } catch (GeneralApplicationException e) {
            logger.error(e);
        } catch (IllegalArgumentException e) {
            logger.error("Некорректный ключ фигуры");
        }
    }

    private static CommandLine parseCmd(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption(FLAG_F, "outToFile", true, "Вывод в файл");
        options.addOption(FLAG_C, "outToConsole", false, "Вывод в консоль");
        return parser.parse(options, args);
    }
}