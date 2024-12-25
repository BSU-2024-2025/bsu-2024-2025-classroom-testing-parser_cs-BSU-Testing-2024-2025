package org.example.shapesealed;

public interface ShapeVisitor<T> {
    T visit(Circle circle);
    T visit(Rectangle rectangle);
    T visit(Triangle triangle);
}
