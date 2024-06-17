package ru.focus.taskin.multtable.scanner;

import ru.focus.taskin.multtable.exceptions.OutOfRangeValuesException;

public class ScanValidator {
    public int validateInputData(int tableSize, int minNum, int maxNum) throws OutOfRangeValuesException {

        if (tableSize >= minNum && tableSize <= maxNum) {
            return tableSize;
        } else {
            throw new OutOfRangeValuesException(String.format("Вы ввели число не в диапазоне от %d до %d", minNum, maxNum));
        }
    }

    public int checkInputData(String input) throws OutOfRangeValuesException {

        if (input.length() > 2) {
            throw new OutOfRangeValuesException("В строке больше 2-х символов");
        } else {
            return Integer.parseInt(input.trim());
        }
    }
}
