package ru.focus.taskin;

public interface AlgorithmFactory<T extends SeriesAlgorithm> {
    T create(long start, long end);
}
