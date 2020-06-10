package my.junit.ch07;

import java.io.InputStream;

public interface ConnectionFactory {
    InputStream getData() throws Exception;
}
