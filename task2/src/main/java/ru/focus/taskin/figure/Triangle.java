package ru.focus.taskin.figure;

import ru.focus.taskin.exceptions.IncorrectParamsException;

public class Triangle implements Figure {
    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(double sideA, double sideB, double sideC) throws IncorrectParamsException {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new IncorrectParamsException("Стороны треугольника должны быть больше 0");
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public String calculateInfo() throws IncorrectParamsException {
        if (!canMakeTriangle(sideA, sideB, sideC)) {
            throw new IncorrectParamsException("в треугольнике сумма любых двух сторон должна быть больше третьей");
        }
        return """
                Тип фигуры: %s
                Площадь: %.3f кв км
                Периметр: %.3f км
                Длина стороны А: %.3f км
                Противолежащий угол: %.2f градусов
                Длина стороны B: %.3f км
                Противолежащий угол: %.2f градусов
                Длина стороны C: %.3f км
                Противолежащий угол: %.2f градусов
                """.formatted(printFigureType(), calculateArea(), calculatePerimeter(),
                sideA, calculateAlpha(), sideB, calculateBeta(), sideC, calculateGamma());
    }

    @Override
    public String printFigureType() {
        return FigureType.TRIANGLE.getTranslate();
    }

    @Override
    public Double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public Double calculateArea() {
        double semiPer = calculatePerimeter() / 2;
        return Math.sqrt(semiPer * (semiPer - sideA) * (semiPer - sideB) * (semiPer - sideC));
    }

    private static boolean canMakeTriangle(double sideA, double sideB, double sideC) {
        return sideA + sideB > sideC && sideA + sideC > sideB && sideB + sideC > sideA;
    }

    public Double calculateAlpha() {
        return Math.toDegrees(Math.acos((sideB * sideB + sideC * sideC - sideA * sideA) / (2 * sideB * sideC)));
    }

    public Double calculateBeta() {
        return Math.toDegrees(Math.acos((sideA * sideA + sideC * sideC - sideB * sideB) / (2 * sideA * sideC)));
    }

    public Double calculateGamma() {
        return 180 - calculateAlpha() - calculateBeta();
    }
}
