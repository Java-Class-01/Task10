import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testMainMethodExists() {
        // Verify the main method exists and can be called in headless mode
        // Set headless system property to prevent UI from showing
        System.setProperty("headless", "true");
        try {
            assertDoesNotThrow(() -> {
                Main.main(new String[]{});
            }, "main() method should not throw exceptions");
        } finally {
            System.clearProperty("headless");
        }
    }

    @Test
    public void testMainMethodWithArgs() {
        // Test main method with arguments in headless mode
        System.setProperty("headless", "true");
        try {
            assertDoesNotThrow(() -> {
                Main.main(new String[]{"arg1", "arg2"});
            }, "main() method should handle arguments gracefully");
        } finally {
            System.clearProperty("headless");
        }
    }
}

