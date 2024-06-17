package ru.focus.taskin.figure;

import ru.focus.taskin.exceptions.IncorrectParamsException;

public class Rectangle implements Figure {
    private final double length;
    private final double width;

    public Rectangle(double length, double width) throws IncorrectParamsException {
        if (length <= 0 || width <= 0) {
            throw new IncorrectParamsException("Стороны прямоугольника должны быть больше 0");
        }
        if (length >= width) {
            this.length = length;
            this.width = width;
        } else {
            this.length = width;
            this.width = length;
        }
    }

    @Override
    public String calculateInfo() {
        return """
                Тип фигуры: %s
                Площадь: %.3f кв км
                Периметр: %.3f км
                Длина диагонали: %.3f км
                Ширина: %.3f км
                Высота: %.3f км
                """.formatted(printFigureType(), calculateArea(), calculatePerimeter(),
                calculateDiagonal(), length, width);
    }

    @Override
    public String printFigureType() {
        String figType;

        if (isSquare(length, width)) {
            figType = "Квадрат";
        } else {
            figType = FigureType.RECTANGLE.getTranslate();
        }
        return figType;
    }

    @Override
    public Double calculatePerimeter() {
        return 2 * (length + width);
    }

    @Override
    public Double calculateArea() {
        return length * width;
    }

    public Double calculateDiagonal() {
        return Math.sqrt(Math.pow(length, 2) + Math.pow(width, 2));
    }

    private boolean isSquare(double length, double width) {
        return length == width;
    }
}
