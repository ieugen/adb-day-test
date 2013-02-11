package ro.ieugen.http.fileserver;

import com.google.common.io.CharStreams;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.net.URI;

public class HttpFileServerTest {

    private static final Logger LOG = LoggerFactory.getLogger(HttpFileServerTest.class);

    private static final int PORT = 9111;
    private HttpStaticFileServer server = new HttpStaticFileServer(PORT);
    private HttpClient client;

    @Before
    public void setUp() throws Exception {
        if (!server.isRunning()) {
            server.run();
        }
        client = new DefaultHttpClient();
    }

    @After
    public void tearDown() throws Exception {
        server.close();
    }

    @Test
    public void testFileDownload() throws Exception {
        HttpUriRequest getFile = new HttpGet(pathToUri("pom.xml"));
        HttpResponse response = client.execute(getFile);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        String fileContent = CharStreams.toString(new InputStreamReader(response.getEntity().getContent()));
        assertThat(fileContent).contains("netty")
                .contains("ro.ieugen.day");

        LOG.info(response.toString());
    }

    private URI pathToUri(String path) {
        return URI.create(String.format("http://localhost:%d/%s", PORT, path));
    }
}
