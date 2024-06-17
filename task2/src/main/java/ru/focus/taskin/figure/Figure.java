package ru.focus.taskin.figure;

import ru.focus.taskin.exceptions.GeneralApplicationException;

public interface Figure {
    String calculateInfo() throws GeneralApplicationException;

    String printFigureType();

    Double calculatePerimeter();

    Double calculateArea();
}
