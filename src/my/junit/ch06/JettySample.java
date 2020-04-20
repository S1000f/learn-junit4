package my.junit.ch06;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class JettySample {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ContextHandler root = new ContextHandler(server,"/");
        root.setResourceBase("./pom.xml");
        root.setHandler(new ResourceHandler());

        server.start();
    }
}
