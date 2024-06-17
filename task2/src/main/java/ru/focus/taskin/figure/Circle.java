package ru.focus.taskin.figure;

import ru.focus.taskin.exceptions.IncorrectParamsException;

public class Circle implements Figure {
    private final double radius;

    public Circle(double radius) throws IncorrectParamsException {
        if (radius <= 0) {
            throw new IncorrectParamsException("Радиус должен быть больше 0");
        }
        this.radius = radius;
    }

    @Override
    public String calculateInfo() {
        return """
                Тип фигуры: %s
                Площадь: %.3f кв км
                Периметр: %.3f км
                Радиус: %.3f км
                Диаметр: %.3f км
                """.formatted(printFigureType(), calculateArea(), calculatePerimeter(), radius, calculateDiameter());
    }

    @Override
    public String printFigureType() {
        return FigureType.CIRCLE.getTranslate();
    }

    @Override
    public Double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public Double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    public Double calculateDiameter() {
        return 2 * radius;
    }
}