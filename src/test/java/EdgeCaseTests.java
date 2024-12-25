import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeCaseTests {

    @Test
    public void testEmptyShapeList() throws InterruptedException, ExecutionException {
        ShapeProcessor processor = new ShapeProcessor(3, mock(ShapeOperations.class));
        List<Double> results = processor.processShapes(Collections.emptyList(), Optional.empty());
        assertTrue(results.isEmpty());
        processor.shutdown();
    }

    @Test
    public void testInvalidOptionalKey() {
        Map<Optional<Object>, Function<Shape, Double>> operations = ShapeOperations.createOperations();
        assertNull(operations.get(Optional.of("invalid_key")));
    }
}
