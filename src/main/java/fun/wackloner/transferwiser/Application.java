package fun.wackloner.transferwiser;

import fun.wackloner.transferwiser.server.ApplicationServer;

/**
 * Main application class.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationServer.newInstance().start(true);
    }
}
