package org.example.shapeoperations;

import org.example.shapesealed.*;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ShapeOperations implements ShapeVisitor<Double> {
    private final Map<Optional<Object>, Function<Shape, Double>> operations;

    public ShapeOperations(Map<Optional<Object>, Function<Shape, Double>> operations) {
        this.operations = operations;
    }

    @Override
    public Double visit(Circle circle) {
        return operations.get(Optional.empty()).apply(circle);
    }

    @Override
    public Double visit(Rectangle rectangle) {
        return operations.get(Optional.empty()).apply(rectangle);
    }

    @Override
    public Double visit(Triangle triangle) {
        return operations.get(Optional.empty()).apply(triangle);
    }

    public static Map<Optional<Object>, Function<Shape, Double>> createOperations() {
        return Map.of(
                Optional.empty(), shape -> {
                    if (shape instanceof Circle(double radius)) {
                        return Math.PI * radius * radius;  // Area of Circle
                    } else if (shape instanceof Rectangle(double width, double height)) {
                        return width * height;  // Area of Rectangle
                    } else if (shape instanceof Triangle(double base, double height)) {
                        return 0.5 * base * height;  // Area of Triangle
                    }
                    return 0.0;
                },
                Optional.of("Perimeter"), shape -> {
                    if (shape instanceof Circle(double radius)) {
                        return 2 * Math.PI * radius;  // Perimeter of Circle
                    } else if (shape instanceof Rectangle(double width, double height)) {
                        return 2 * (width + height);  // Perimeter of Rectangle
                    } else if (shape instanceof Triangle(double base, double height)) {
                        return base + height + Math.sqrt(base * base + height * height);  // Perimeter of Triangle (hypothetical)
                    }
                    return 0.0;
                }
        );
    }
}

