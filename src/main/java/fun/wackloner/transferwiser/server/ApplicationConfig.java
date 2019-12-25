package fun.wackloner.transferwiser.server;

import fun.wackloner.transferwiser.repository.AccountRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("resources")
public class ApplicationConfig extends ResourceConfig {
    ApplicationConfig(AccountRepository accountRepository) {
        packages("fun.wackloner.transferwiser");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(accountRepository).to(AccountRepository.class);
            }
        });
    }
}
