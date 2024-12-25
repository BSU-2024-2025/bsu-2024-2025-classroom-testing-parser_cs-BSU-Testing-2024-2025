package org.example.shapeoperations;

import org.example.shapesealed.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class ShapeProcessor {

    private final ExecutorService executor;
    private final ShapeOperations shapeOperations;

    public ShapeProcessor(int threadPoolSize, ShapeOperations shapeOperations) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
        this.shapeOperations = shapeOperations;
    }

    public List<Double> processShapes(List<Shape> shapes, Optional<Object> condition) throws InterruptedException, ExecutionException {
        List<Callable<Double>> tasks = shapes.stream()
                .map(shape -> (Callable<Double>) () -> shape.accept(shapeOperations))
                .toList();

        List<Future<Double>> results = executor.invokeAll(tasks);
        List<Double> outputs = new ArrayList<>();
        for (Future<Double> result : results) {
            outputs.add(result.get());
        }

        return outputs;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
