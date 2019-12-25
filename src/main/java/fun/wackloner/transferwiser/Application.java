package fun.wackloner.transferwiser;

import fun.wackloner.transferwiser.repository.InMemoryAccountRepository;
import fun.wackloner.transferwiser.server.ApplicationServer;

/**
 * Main application class.
 */
public class Application {
    public static void main(String[] args) {
        var accountRepository = new InMemoryAccountRepository();
        ApplicationServer.newInstance(accountRepository).start(true);
    }
}
