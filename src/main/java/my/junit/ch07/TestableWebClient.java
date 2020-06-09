package my.junit.ch07;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestableWebClient extends WebClient {

    private HttpURLConnection connection;

    public void setHttpURLConnection(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
    }

    @Override
    protected HttpURLConnection createHttpURLConnection(URL url) throws IOException {
        return this.connection;
    }
}
