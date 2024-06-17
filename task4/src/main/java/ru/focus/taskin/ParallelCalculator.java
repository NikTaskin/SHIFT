package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelCalculator {
    private static final Logger LOGGER = LogManager.getLogger(ParallelCalculator.class);

    public double parallelCalculate(long serialLength, AlgorithmFactory<?> factory) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Collection<Callable<Double>> tasks = createTasks(serialLength, factory, numThreads);

        double totalSum = 0;

        try {
            List<Double> results = executor.invokeAll(tasks)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage());
                            Thread.currentThread().interrupt();
                            return 0.0;
                        }
                    })
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

            for (double partialSum : results) {
                totalSum += partialSum;
            }
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }

        return totalSum;
    }

    private Collection<Callable<Double>> createTasks(long seriesLength, AlgorithmFactory<?> factory, int numThreads) {
        Collection<Callable<Double>> tasks = new ArrayList<>();
        long chunkSize = seriesLength / numThreads;
        long extraElements = seriesLength % numThreads;
        for (int i = 0; i < numThreads; i++) {
            long start = i * chunkSize + Math.min(i, extraElements);
            long end = start + chunkSize + (i < extraElements ? 1 : 0);
            tasks.add(() -> factory.create(start, end).calculate());
        }
        return tasks;
    }
}
