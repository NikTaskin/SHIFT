package ru.focus.taskin;

class PowerOf2 implements SeriesAlgorithm {
    private final long start;
    private final long end;

    public PowerOf2(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public double calculate() {
        double sum = 0;
        for (long i = start; i < end; i++) {
            sum += 1.0 / Math.pow(2, i);
        }
        return sum;
    }
}