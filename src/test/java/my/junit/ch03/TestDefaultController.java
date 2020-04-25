package my.junit.ch03;

import my.junit.ch03.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDefaultController {
    private DefaultController controller;
    private Request request;
    private RequestHandler handler;


    private class SampleRequest implements Request {

        private static final String DEFAULT_NAME = "Test";
        private String name;

        public SampleRequest(String name) {
            this.name = name;
        }

        public SampleRequest() {
            this(DEFAULT_NAME);
        }

        public String getName() {
            return this.name;
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

    private class SampleExceptionHandler implements RequestHandler {

        public Response process(Request request) throws Exception {
            throw new Exception("error processing request");
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

    @Test
    public void testProcessRequestAnswersErrorResponse() {
        SampleRequest request = new SampleRequest("testError");
        SampleExceptionHandler handler = new SampleExceptionHandler();
        controller.addHandler(request, handler);
        Response response = controller.processRequest(request);

        assertNotNull("must not return a null response", response);
        assertEquals(ErrorResponse.class, response.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void testGetHandlerNotDefined() {
        SampleRequest request = new SampleRequest("testNotDefined");

        // here RuntimeException will occur
        controller.getHandler(request);
    }

    @Test(expected = RuntimeException.class)
    public void testAddRequestDuplicateName() {
        SampleRequest request = new SampleRequest();
        SampleHandler handler = new SampleHandler();

        // here RuntimeException will occur
        controller.addHandler(request, handler);
    }

    @Ignore(value = "ignore for now until we decide a decent time-limit")
    @Test(timeout = 80)
    public void testProcessMultipleRequestsTimeout() {
        Request request;
        Response response = new SampleResponse();
        RequestHandler handler = new SampleHandler();
        for(int i = 0; i < 99999; i++) {
            request = new SampleRequest(String.valueOf(i));
            controller.addHandler(request, handler);
            response = controller.processRequest(request);

            assertNotNull(response);
            assertNotSame(ErrorResponse.class, response.getClass());
        }
    }

}
