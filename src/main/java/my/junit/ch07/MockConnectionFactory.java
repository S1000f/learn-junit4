package my.junit.ch07;

import java.io.InputStream;

public class MockConnectionFactory implements ConnectionFactory {

    private InputStream inputStream;

    public void setData(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getData() throws Exception {
        return this.inputStream;
    }
}
