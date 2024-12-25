import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeTest {

    @Test
    public void testCircleAccept() {
        ShapeVisitor<Double> visitor = mock(ShapeVisitor.class);
        Circle circle = new Circle(5);
        when(visitor.visit(circle)).thenReturn(78.5);

        Double result = circle.accept(visitor);

        assertEquals(78.5, result);
        verify(visitor).visit(circle);
    }

    @Test
    public void testRectangleAccept() {
        ShapeVisitor<Double> visitor = mock(ShapeVisitor.class);
        Rectangle rectangle = new Rectangle(3, 4);
        when(visitor.visit(rectangle)).thenReturn(12.0);

        Double result = rectangle.accept(visitor);

        assertEquals(12.0, result);
        verify(visitor).visit(rectangle);
    }

    @Test
    public void testTriangleAccept() {
        ShapeVisitor<Double> visitor = mock(ShapeVisitor.class);
        Triangle triangle = new Triangle(6, 8);
        when(visitor.visit(triangle)).thenReturn(24.0);

        Double result = triangle.accept(visitor);

        assertEquals(24.0, result);
        verify(visitor).visit(triangle);
    }
}
