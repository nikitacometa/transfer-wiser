package fun.wackloner.transferwiser;

/**
 * Main application class.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationServer.newInstance().start(true);
    }
}
