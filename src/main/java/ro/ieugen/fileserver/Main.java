package ro.ieugen.fileserver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.ieugen.fileserver.config.DefaultServerConfiguration;
import ro.ieugen.fileserver.http.HttpStaticFileServer;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    /**
     * Change the default config file using -D JVM option.
     */
    public static final String SERVER_CONFIG_SYSTEM_PROPERTY = "http.server.config";

    public static void main(String[] args) throws IOException {
        String fileName = System.getProperty(SERVER_CONFIG_SYSTEM_PROPERTY, "http-server-conf.yml");
        LOG.info("Configuration file is {} from system property {}", fileName, SERVER_CONFIG_SYSTEM_PROPERTY);

        DefaultServerConfiguration conf = readConfiguration(fileName);
        LOG.info("Server configuration is {}", conf);
        HttpStaticFileServer server = new HttpStaticFileServer(conf);
        server.run();
    }

    public static DefaultServerConfiguration readConfiguration(String fileName) throws IOException {
        return parseConfiguration(Files.toString(new File(fileName), Charsets.UTF_8));
    }

    public static DefaultServerConfiguration parseConfiguration(String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper.readValue(content, DefaultServerConfiguration.class);
    }
}
