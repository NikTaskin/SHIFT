package ru.focus.taskin.figure;

public enum FigureType {
    CIRCLE(1, "Круг"),
    RECTANGLE(2, "Прямоугольник"),
    TRIANGLE(3, "Треугольник");
    private final int countOfParams;
    private final String translate;

    FigureType(int countOfParams, String translate) {
        this.countOfParams = countOfParams;
        this.translate = translate;
    }

    public int getCountOfParams() {
        return countOfParams;
    }

    public String getTranslate() {
        return translate;
    }
}
