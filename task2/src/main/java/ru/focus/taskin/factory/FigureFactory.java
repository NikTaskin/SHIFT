package ru.focus.taskin.factory;

import ru.focus.taskin.exceptions.IncorrectParamsException;
import ru.focus.taskin.figure.*;

import java.util.List;

import static ru.focus.taskin.figure.FigureType.*;

public class FigureFactory {
    public Figure createFigure(FigureType types, List<String> inputData) throws IncorrectParamsException {
        String[] params = inputData.get(1).split(" ");
        return switch (types) {
            case CIRCLE -> {
                try {
                    if (params.length == CIRCLE.getCountOfParams()) {
                        yield new Circle(Double.parseDouble(params[0]));
                    } else {
                        throw new IncorrectParamsException("Для создания круга нужен один параметр");
                    }
                } catch (NumberFormatException e) {
                    throw new IncorrectParamsException("Параметры фигуры должны быть в формате чисел");
                }
            }
            case RECTANGLE -> {
                try {
                    if (params.length == RECTANGLE.getCountOfParams()) {
                        yield new Rectangle(Double.parseDouble(params[0]), Double.parseDouble(params[1]));
                    } else {
                        throw new IncorrectParamsException("Для создания прямоугольника нужно два параметра");
                    }
                } catch (NumberFormatException e) {
                    throw new IncorrectParamsException("Параметры фигуры должны быть в формате чисел");
                }
            }
            case TRIANGLE -> {
                try {
                    if (params.length == TRIANGLE.getCountOfParams()) {
                        yield new Triangle(Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
                    } else {
                        throw new IncorrectParamsException("Для создания треугольника нужно три параметра");
                    }
                } catch (NumberFormatException e) {
                    throw new IncorrectParamsException("Параметры фигуры должны быть в формате чисел");
                }
            }
        };
    }
}

