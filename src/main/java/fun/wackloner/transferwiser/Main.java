package fun.wackloner.transferwiser;

import fun.wackloner.transferwiser.service.AccountService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    private static Server setUpServer() {
        var contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        var servletHolder = contextHandler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        //TODO: auto-build classnames string from package
        servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                AccountService.class.getCanonicalName());

        var server = new Server(8080);
        server.setHandler(contextHandler);

        return server;
    }

    public static void main(String[] args) throws Exception {
        var server = setUpServer();

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}
