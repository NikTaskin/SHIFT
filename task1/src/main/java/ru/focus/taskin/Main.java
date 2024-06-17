package ru.focus.taskin;

import ru.focus.taskin.multtable.exceptions.OutOfRangeValuesException;
import ru.focus.taskin.multtable.scanner.ScanValidator;
import ru.focus.taskin.multtable.table.TableRender;

import java.io.PrintWriter;
import java.util.Scanner;

import static ru.focus.taskin.multtable.table.TableCalculator.calculateMatrixForCells;

public class Main {
    public static void main(String[] args) {
        final int minNum = 1;
        final int maxNum = 32;
        Scanner scanner = new Scanner(System.in);
        PrintWriter consoleOutput = new PrintWriter(System.out, true);
        consoleOutput.println(String.format("Введите число от %d до %d включительно:", minNum, maxNum));
        String strIn = scanner.next();

        try {
            ScanValidator scanValidator = new ScanValidator();
            TableRender tableRender = new TableRender();
            int tableSize = scanValidator.validateInputData(scanValidator.checkInputData(strIn), minNum, maxNum);

            tableRender.drawTable(calculateMatrixForCells(tableSize));
        } catch (NumberFormatException nfe) {
            consoleOutput.println("Неверный формат, введите целое число");
        } catch (OutOfRangeValuesException e) {
            consoleOutput.println(e.getMessage());
        }
    }
}