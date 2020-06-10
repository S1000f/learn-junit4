package my.junit.ch07;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class TestWebClient {

    @Test
    public void testGetContentOk() throws Exception {
        // given
        MockConnectionFactory mockConnectionFactory = new MockConnectionFactory();
        WebClient client = new WebClient();

        // when
        mockConnectionFactory.setData(new ByteArrayInputStream("It works".getBytes()));
        String result = client.getContent(mockConnectionFactory);

        // then
        assertEquals("It works", result);
    }
}
