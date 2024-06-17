package ru.focus.taskin.tests;

import org.junit.jupiter.api.Test;
import ru.focus.taskin.exceptions.GeneralApplicationException;
import ru.focus.taskin.exceptions.IncorrectParamsException;
import ru.focus.taskin.factory.FigureFactory;
import ru.focus.taskin.figure.FigureType;
import ru.focus.taskin.figure.Circle;
import ru.focus.taskin.figure.Rectangle;
import ru.focus.taskin.figure.Triangle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestCreateFigures {

    @Test
    void testCreateCircle() throws IncorrectParamsException {
        FigureFactory factory = new FigureFactory();
        List<String> params = new ArrayList<>();
        params.add("");
        params.add("5");
        String expectedInfo = """
                Тип фигуры: Круг
                Площадь: 78,540 кв км
                Периметр: 31,416 км
                Радиус: 5,000 км
                Диаметр: 10,000 км
                """;
        Circle circle = (Circle) factory.createFigure(FigureType.CIRCLE, params);
        assertNotNull(circle);
        assertEquals(expectedInfo, circle.calculateInfo());
    }

    @Test
    void testIncorrectCircleParam() {
        assertThrows(IncorrectParamsException.class, () -> new Circle(0));
    }

    @Test
    void testCreateRectangle() throws GeneralApplicationException {
        FigureFactory factory = new FigureFactory();
        List<String> params = new ArrayList<>();
        params.add("");
        params.add("4 6");
        String expectedInfo = """
                Тип фигуры: Прямоугольник
                Площадь: 24,000 кв км
                Периметр: 20,000 км
                Длина диагонали: 7,211 км
                Ширина: 6,000 км
                Высота: 4,000 км
                """;
        Rectangle rectangle = (Rectangle) factory.createFigure(FigureType.RECTANGLE, params);
        assertNotNull(rectangle);
        assertEquals(expectedInfo, rectangle.calculateInfo());
    }

    @Test
    void testCreateSquare() throws GeneralApplicationException {
        FigureFactory factory = new FigureFactory();
        List<String> params = new ArrayList<>();
        params.add("");
        params.add("5.5 5.5");
        String expectedInfo = """
                Тип фигуры: Квадрат
                Площадь: 30,250 кв км
                Периметр: 22,000 км
                Длина диагонали: 7,778 км
                Ширина: 5,500 км
                Высота: 5,500 км
                """;
        Rectangle rectangle = (Rectangle) factory.createFigure(FigureType.RECTANGLE, params);
        assertNotNull(rectangle);
        assertEquals(expectedInfo, rectangle.calculateInfo());
    }

    @Test
    void testIncorrectRectangleLength() {
        assertThrows(IncorrectParamsException.class, () -> new Rectangle(-1, 5));
    }

    @Test
    void testIncorrectRectangleWidth() {
        assertThrows(IncorrectParamsException.class, () -> new Rectangle(5, 0));
    }

    @Test
    void testCreateTriangle() throws GeneralApplicationException {
        FigureFactory factory = new FigureFactory();
        List<String> params = new ArrayList<>();
        params.add("");
        params.add("3 4 5");
        String expectedInfo = """
                Тип фигуры: Треугольник
                Площадь: 6,000 кв км
                Периметр: 12,000 км
                Длина стороны А: 3,000 км
                Противолежащий угол: 36,87 градусов
                Длина стороны B: 4,000 км
                Противолежащий угол: 53,13 градусов
                Длина стороны C: 5,000 км
                Противолежащий угол: 90,00 градусов
                """;
        Triangle triangle = (Triangle) factory.createFigure(FigureType.TRIANGLE, params);
        assertNotNull(triangle);
        assertEquals(expectedInfo, triangle.calculateInfo());
    }

    @Test
    void testIncorrectTriangleParam() {
        assertThrows(IncorrectParamsException.class, () -> new Triangle(0, -1, 1));
    }

    @Test
    void testCanMakeTriangle() {
        assertThrows(IncorrectParamsException.class, () -> new Triangle(1, 2, 3).calculateInfo());
    }
}
