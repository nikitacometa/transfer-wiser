package fun.wackloner.transferwiser;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

public class ApplicationServer {
    private static final Logger log = LoggerFactory.getLogger(ApplicationServer.class);

    private static final int DEFAULT_SERVER_PORT = 8080;
    private final Server jettyServer;

    private ApplicationServer(Server jettyServer) {
        this.jettyServer = jettyServer;
    }

    public static ApplicationServer newInstance() {
        var config = new ApplicationConfig();
        var servlet = new ServletHolder(new ServletContainer(config));
        var server = new Server(DEFAULT_SERVER_PORT);
        var contextHandler = new ServletContextHandler(server, "/*");
        contextHandler.addServlet(servlet, "/*");

        return new ApplicationServer(server);
    }

    public void run() {
        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            log.error("Jetty server failed!", e);
        } finally {
            jettyServer.destroy();
        }
    }

    @ApplicationPath("resources")
    private static class ApplicationConfig extends ResourceConfig {
        ApplicationConfig() {
            packages("fun.wackloner.transferwiser");
        }
    }
}
