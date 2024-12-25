package org.example.shapesealed;

public record Rectangle(double width, double height) implements Shape {
    @Override
    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
