package my.junit.ch06;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.util.ByteArrayISO8859Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestWebClient {
    private class TestGetContentOkHandler extends AbstractHandler { // Jetty의 AbstractHandler 추상클래스를 상속
        @Override
        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
                throws IOException {
            OutputStream out = response.getOutputStream();
            ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer();
            writer.write("It works");
            writer.flush();

            response.setIntHeader(HttpHeaders.CONTENT_LENGTH, writer.size());
            writer.writeTo(out);
            out.flush();
        }
    }

    private class TestGetContentNotFoundHandler extends AbstractHandler {
        @Override
        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
                throws IOException {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @BeforeClass
    public static void setUp() throws Exception {
        Server server = new Server(8080);

        TestWebClient t = new TestWebClient();

        Context contextOkContent = new Context(server, "/testGetContentOk");
        contextOkContent.setHandler(t.new TestGetContentOkHandler());

        Context contentNotFoundContext = new Context(server, "/testGetContentNotFound");
        contentNotFoundContext.setHandler(t.new TestGetContentNotFoundHandler());

        server.setStopAtShutdown(true);
        server.start();
    }

    @After
    public void tearDown() {
        // stop Jetty server
    }

    @Test
    public void testGetContentOk() throws Exception {
        WebClient client = new WebClient();
        String result = client.getContent(new URL("http://localhost:8080/testGetContentOk"));

        assertEquals("It works", result);
    }

    @Test
    public void testGetContentNotFound() throws Exception {
        WebClient client = new WebClient();
        String result = client.getContent(new URL("http://localhost:8080/testGetContentNotFound"));

        assertNull(result);
    }
}
