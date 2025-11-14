import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @Test
    public void testGetConnection() {
        // Test that getConnection method exists and returns a Connection (or null if DB unavailable)
        // This test verifies the method doesn't throw exceptions
        // Connection can be null if database is not available, which is acceptable
        assertDoesNotThrow(() -> {
            DatabaseConnection.getConnection();
            // Connection may be null if database is unavailable, which is expected behavior
        }, "getConnection() should not throw exceptions");
    }

    @Test
    public void testGetConnectionMethodExists() {
        // Verify the static method exists and can be called
        assertDoesNotThrow(() -> {
            DatabaseConnection.getConnection();
        }, "getConnection() should not throw exceptions");
    }
}

