package my.junit.ch03;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDefaultController {
    private DefaultController controller;
    private Request request;
    private RequestHandler handler;


    private class SampleRequest implements Request {
        public String getName() {
            return "Test";
        }
    }

    private class SampleHandler implements RequestHandler {
        public Response process(Request request) throws Exception {
            return new SampleResponse();
        }
    }

    private class SampleResponse implements Response {
        private static final String NAME = "Test";

        public String getName() {
            return NAME;
        }

        public boolean equals(Object object) {
            boolean result = false;
            if (object instanceof SampleResponse) {
                result = ((SampleResponse)object).getName().equals(getName());
            }

            return result;
        }

        public int hashCode() {
            return NAME.hashCode();
        }
    }


    @Before
    public void instantiate() throws Exception {
        controller = new DefaultController();
        request = new SampleRequest();
        handler = new SampleHandler();
        controller.addHandler(request, handler);
    }

    @Test
    public void testAddHandler() {
        RequestHandler handler2 = controller.getHandler(request);
        assertSame("Handler we set in Controller should be the same handler we will got", handler2, handler);
    }

    @Test
    public void TestProcessRequest() {
        Response response = controller.processRequest(request);
        assertNotNull("Must not null", response);
        assertEquals(new SampleResponse(), response);
    }
}
