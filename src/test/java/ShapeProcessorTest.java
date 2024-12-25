import org.example.shapesealed.Rectangle;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import java.util.*;
import java.util.concurrent.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeProcessorTest {

    @Test
    public void testShapeProcessor() throws InterruptedException, ExecutionException {
        // Create mock operations
        ShapeOperations mockOperations = mock(ShapeOperations.class);
        when(mockOperations.visit(any(Circle.class))).thenReturn(10.0);
        when(mockOperations.visit(any(Rectangle.class))).thenReturn(20.0);
        when(mockOperations.visit(any(Triangle.class))).thenReturn(15.0);

        // Create ShapeProcessor
        ShapeProcessor processor = new ShapeProcessor(2, mockOperations);

        // List of shapes to process
        List<Shape> shapes = List.of(new Circle(2), new Rectangle(4, 5), new Triangle(3, 4));

        // Process the shapes
        List<Double> results = processor.processShapes(shapes, Optional.empty());

        // Assert results
        assertEquals(3, results.size());
        assertEquals(10.0, results.get(0));
        assertEquals(20.0, results.get(1));
        assertEquals(15.0, results.get(2));

        // Verify interactions
        verify(mockOperations).visit(any(Circle.class));
        verify(mockOperations).visit(any(Rectangle.class));
        verify(mockOperations).visit(any(Triangle.class));
    }

    @Test
    public void testOperationsMap() {
        Map<Optional<Object>, Function<Shape, Double>> operations = ShapeOperations.createOperations();
        assertNotNull(operations.get(Optional.empty()));
        assertNotNull(operations.get(Optional.of("perimeter")));
    }
}
