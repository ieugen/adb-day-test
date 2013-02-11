package ro.ieugen.fileserver.http;

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
import ro.ieugen.fileserver.Main;
import ro.ieugen.fileserver.config.DefaultServerConfiguration;

import java.io.InputStreamReader;
import java.net.URI;

public class HttpFileServerTest {

    private static final Logger LOG = LoggerFactory.getLogger(HttpFileServerTest.class);

    private static final String CONFIG_FILE = "src/test/resources/http-server-conf-sample.yml";
    private DefaultServerConfiguration configuration;
    private HttpStaticFileServer server;
    private HttpClient client;

    @Before
    public void setUp() throws Exception {
        configuration = Main.readConfiguration(CONFIG_FILE);
        server = new HttpStaticFileServer(configuration);
        server.run();
        client = new DefaultHttpClient();
    }

    @After
    public void tearDown() throws Exception {
        server.close();
    }

    public DefaultServerConfiguration getConfiguration() {
        return configuration;
    }

    @Test
    public void testFileDownload() throws Exception {
        HttpUriRequest getFileRequest = new HttpGet(pathToUri("pom.xml"));
        HttpResponse response = client.execute(getFileRequest);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        String fileContent = CharStreams.toString(new InputStreamReader(response.getEntity().getContent()));
        assertThat(fileContent).contains("netty")
                .contains("ro.ieugen.day");

        LOG.info(response.toString());
    }

    private URI pathToUri(String path) {
        return URI.create(String.format("http://localhost:%d/%s", configuration.getPort(), path));
    }
}
