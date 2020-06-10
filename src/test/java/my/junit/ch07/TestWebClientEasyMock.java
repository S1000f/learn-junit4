package my.junit.ch07;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class TestWebClientEasyMock {

    private ConnectionFactory factory;
    private InputStream stream;

    @Before
    public void setUp() {
        factory = createMock("factory", ConnectionFactory.class);
        stream = createMock("stream", InputStream.class);
    }

    @Test
    public void testGetContentOk() throws Exception {
        expect(factory.getData()).andReturn(stream);
        expect(stream.read()).andReturn(new Integer((byte)'W'));
        expect(stream.read()).andReturn(new Integer((byte)'o'));
        expect(stream.read()).andReturn(new Integer((byte)'r'));
        expect(stream.read()).andReturn(new Integer((byte)'k'));
        expect(stream.read()).andReturn(new Integer((byte)'s'));
        expect(stream.read()).andReturn(new Integer((byte)'!'));
        expect(stream.read()).andReturn(-1);
        stream.close();

        replay(factory);
        replay(stream);

        WebClient client = new WebClient();
        String result = client.getContent(factory);

        assertEquals("Works!", result);
    }
}
