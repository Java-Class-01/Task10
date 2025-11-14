import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsingPDFTest {

    @Test
    public void testUsingPDFInstantiation() {
        // Test that UsingPDF can be instantiated in headless mode (no UI)
        // This test verifies the constructor doesn't throw exceptions
        assertDoesNotThrow(() -> {
            new UsingPDF(true); // headless mode
        }, "UsingPDF constructor should not throw exceptions");
    }

    @Test
    public void testUsingPDFInstanceNotNull() {
        // Test that UsingPDF instance is created successfully in headless mode
        UsingPDF pdf = new UsingPDF(true); // headless mode
        assertNotNull(pdf, "UsingPDF instance should not be null");
    }
}

