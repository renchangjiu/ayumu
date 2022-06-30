package com.douqz;

/**
 * @author yui
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        HttpServer server = new HttpServer();
        server.start(8080);
        // HttpServer.start();
        // Server server = new Server(port);
        // ServletContextHandler context = new ServletContextHandler(server, "/");
        // context.addServlet(MyServlet.class, "/*");
        // server.start();
    }
}
