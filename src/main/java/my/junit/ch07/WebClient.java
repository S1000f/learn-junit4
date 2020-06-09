package my.junit.ch07;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebClient {

    public String getContent(URL url) {
        StringBuffer content = new StringBuffer();
        try {
            HttpURLConnection conn = createHttpURLConnection(url);
            conn.setDoInput(true);

            InputStream is = conn.getInputStream();
            int count;
            while (-1 != (count = is.read())) {
                content.append(new String(Character.toChars(count)));
            }

        } catch (IOException e) {
            return null;
        }
        return content.toString();
    }

    protected HttpURLConnection createHttpURLConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }
}
