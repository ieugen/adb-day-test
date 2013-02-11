package ro.ieugen.fileserver.config;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import ro.ieugen.fileserver.Main;

import java.io.File;

public class DefaultServerConfigurationTest {

    private final String configName = "src/test/resources/http-server-conf-sample.yml";

    @Test
    public void testWeCanReadTheConfiguration() throws Exception {
        DefaultServerConfiguration configuration = Main.parseConfiguration(Files.toString(new File(configName), Charsets.UTF_8));

        assertThat(configuration.getPort()).isEqualTo(9112);
        assertThat(configuration.getRoot()).isEqualTo("root/dir1");
        assertThat(configuration.getWorkerThreadCount()).isEqualTo(DefaultServerConfiguration.DEFAULT_WORKER_THREAD_COUNT);
    }
}
