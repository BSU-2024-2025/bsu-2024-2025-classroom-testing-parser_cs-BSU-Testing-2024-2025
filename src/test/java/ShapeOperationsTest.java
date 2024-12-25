import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Optional;

public class ShapeOperationsTest {

    @Test
    public void testCreateOperations() {
        Map<Optional<Object>, Function<Shape, Double>> operations = ShapeOperations.createOperations();

        Circle circle = new Circle(5);
        Rectangle rectangle = new Rectangle(4, 6);
        Triangle triangle = new Triangle(3, 4);

        assertEquals(Math.PI * 25, operations.get(Optional.empty()).apply(circle));
        assertEquals(24.0, operations.get(Optional.empty()).apply(rectangle));
        assertEquals(6.0, operations.get(Optional.empty()).apply(triangle));

        assertEquals(2 * Math.PI * 5, operations.get(Optional.of("perimeter")).apply(circle));
        assertEquals(20.0, operations.get(Optional.of("perimeter")).apply(rectangle));
        assertEquals(12.0 + Math.sqrt(25), operations.get(Optional.of("perimeter")).apply(triangle));
    }
}

