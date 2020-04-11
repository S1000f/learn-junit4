package my.junit.ch03;

public class ErrorResponse implements Response {
    private Request originRequest;
    private Exception originException;

    public ErrorResponse(Request request, Exception e) {
        this.originRequest = request;
        this.originException = e;
    }

    public Request getOriginRequest() {
        return this.originRequest;
    }

    public Exception getOriginException() {
        return this.originException;
    }


    @Override
    public String getName() {
        return null;
    }
}
