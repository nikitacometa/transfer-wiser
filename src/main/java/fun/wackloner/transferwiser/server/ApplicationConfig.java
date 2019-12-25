package fun.wackloner.transferwiser.server;

import fun.wackloner.transferwiser.repository.AccountRepository;
import fun.wackloner.transferwiser.repository.InMemoryAccountRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("resources")
public class ApplicationConfig extends ResourceConfig {
    ApplicationConfig() {
        packages("fun.wackloner.transferwiser");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new InMemoryAccountRepository()).to(AccountRepository.class);
            }
        });
    }
}
