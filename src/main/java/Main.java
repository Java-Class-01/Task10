public class Main {
    public static void main(String[]args){
        // Check for headless mode system property (useful for testing)
        // Check both the system property and GraphicsEnvironment
        boolean headless = Boolean.getBoolean("java.awt.headless") || 
                          "true".equalsIgnoreCase(System.getProperty("headless")) ||
                          java.awt.GraphicsEnvironment.isHeadless();
        new UsingPDF(headless);
    }
}
