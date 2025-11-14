public class Main {
    public static void main(String[]args){
        // Check for headless mode system property (useful for testing)
        boolean headless = Boolean.getBoolean("java.awt.headless") || 
                          "true".equalsIgnoreCase(System.getProperty("headless"));
        new UsingPDF(headless);
    }
}
