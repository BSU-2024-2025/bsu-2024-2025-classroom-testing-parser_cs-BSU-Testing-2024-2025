package org.example.shapesealed;

public sealed interface Shape permits Circle, Rectangle, Triangle {
    <T> T accept(ShapeVisitor<T> visitor);
}
