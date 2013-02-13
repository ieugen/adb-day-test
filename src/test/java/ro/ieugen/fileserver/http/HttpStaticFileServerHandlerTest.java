package ro.ieugen.fileserver.http;

import org.junit.Before;

import java.io.File;

public class HttpStaticFileServerHandlerTest {

    private HttpStaticFileServerHandler handler;
    private final File root = new File("src/test/resources/root");

    @Before
    public void setUp() throws Exception {
        handler = new HttpStaticFileServerHandler(root.getCanonicalPath());
    }
}
