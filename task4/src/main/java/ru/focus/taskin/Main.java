package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите значение N: ");
        String input = scanner.next();

        try {
            long seriesLength = Long.parseLong(input);
            if (seriesLength <= 0) {
                LOGGER.error("N должно быть больше 0");
                return;
            }
            var runner = new ParallelCalculator();
            double result = runner.parallelCalculate(seriesLength, (PowerOf2::new));
            System.out.println(result);
        } catch (NumberFormatException e) {
            LOGGER.error("Введите целое положительно число");
        }
    }
}
