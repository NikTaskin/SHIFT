package ru.focus.taskin.multtable.table;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TableCalculator {
    public static int[][] calculateMatrixForCells(int tableSize) {
        int[][] matrix = new int[tableSize + 1][tableSize + 1];
        for (int i = 1; i <= tableSize; i++) {
            for (int j = 1; j <= tableSize; j++) {
                matrix[i][j] = i * j;
            }
        }
        return matrix;
    }
}
