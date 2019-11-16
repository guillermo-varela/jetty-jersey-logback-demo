package com.blogspot.nombre_temp.jetty.jersey.logback.demo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.nombre_temp.jetty.jersey.logback.demo.resource.HealthResource;

public class DemoStarter {

    private static Logger logger = LoggerFactory.getLogger(DemoStarter.class);

    public static void main(String[] args) {
        logger.info("Starting!");
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        contextHandler.setContextPath("/");

        int acceptors = Runtime.getRuntime().availableProcessors();

        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(acceptors + 6, 1);
        final Server jettyServer = new Server(queuedThreadPool);

        ServerConnector serverConnector = new ServerConnector(jettyServer, acceptors, -1);
        serverConnector.setPort(8080);
        serverConnector.setAcceptQueueSize(10);

        jettyServer.addConnector(serverConnector);
        jettyServer.setHandler(contextHandler);

        ServletHolder jerseyServlet = contextHandler.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES, HealthResource.class.getPackage().getName());

        try {
            jettyServer.start();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        logger.info("Stopping!");

                        jettyServer.stop();
                        jettyServer.destroy();
                    } catch (Exception e) {
                        logger.error("Error on shutdown", e);
                    }
                }
            });

            jettyServer.join();
        } catch (Exception e) {
            logger.error("Error starting", e);
        }
    }
}
