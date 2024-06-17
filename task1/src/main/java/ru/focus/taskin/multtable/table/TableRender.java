package ru.focus.taskin.multtable.table;

import java.io.PrintWriter;

public class TableRender {
    private static final String BORDER = "-";
    private static final String STICK = "|";
    private static final String EMPTY = " ";
    public static final int FIRST_ORDER = 10;

    /**
     * Метод drawTable отрисовывает matrix в формате таблицы умножения.
     *
     * @param matrix должен быть не пустой и квадратной матрицей.
     */
    public void drawTable(int[][] matrix) {
        if (!checkSquareMatrix(matrix)) {
            System.out.println("Неверный формат матрицы");
            return;
        }
        int tableSize = matrix.length - 1;
        StringBuilder firstString = new StringBuilder(EMPTY);
        PrintWriter consoleOutput = new PrintWriter(System.out, true);
        int digitsInMaxNum = (int) (Math.log10(tableSize * tableSize) + 1);
        int digitsInNum = (int) (Math.log10(tableSize) + 1);
        String frame = makeFrame(tableSize, digitsInMaxNum, digitsInNum);
        if (tableSize >= FIRST_ORDER) {
            firstString.append(EMPTY);
        }
        firstString.append(makeFirstLine(tableSize, digitsInMaxNum));
        consoleOutput.println(firstString);
        consoleOutput.println(frame);
        for (int i = 1; i <= tableSize; i++) {
            String res = makeFirstNumInLine(tableSize, i) +
                    makeCells(tableSize, matrix, i, digitsInMaxNum);
            consoleOutput.println(res);
            consoleOutput.println(frame);
        }
    }

    private String makeFirstLine(int tableSize, int digitsInMaxNum) {
        int[][] matrix = new int[1][tableSize + 1];
        for (int i = 1; i <= tableSize; i++) {
            matrix[0][i] = i;
        }
        return makeCells(tableSize, matrix, 0, digitsInMaxNum);
    }

    private String makeFirstNumInLine(int tableSize, int nextNum) {
        return (tableSize < FIRST_ORDER || nextNum >= FIRST_ORDER) ? String.valueOf(nextNum) :
                EMPTY.repeat((int) (Math.log10(tableSize))) + nextNum;
    }

    private String makeCells(int tableSize, int[][] matrixForCells, int lineCounter, int digitsInMaxNum) {
        StringBuilder res = new StringBuilder();
        for (int j = 1; j <= tableSize; j++) {
            var num = matrixForCells[lineCounter][j];
            var digitsInNum = (int) (Math.log10(num) + 1);
            res.append(STICK).append(EMPTY.repeat(digitsInMaxNum - digitsInNum)).append(num);
        }
        return res.toString();
    }

    private String makeFrame(int tableSize, int digitsInMaxNum, int digitsInNum) {
        StringBuilder res;
        String part;

        res = new StringBuilder(BORDER.repeat(digitsInNum));
        part = "+" + BORDER.repeat(digitsInMaxNum);
        res.append(part.repeat(Math.max(0, tableSize)));
        return res.toString();
    }

    private static boolean checkSquareMatrix(int[][] matrix) {
        if (matrix.length == 0) {
            return false;
        }
        for (int[] arr : matrix) {
            if (matrix.length != arr.length) {
                return false;
            }
        }
        return true;
    }
}
