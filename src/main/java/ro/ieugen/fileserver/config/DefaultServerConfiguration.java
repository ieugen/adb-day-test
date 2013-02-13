package ro.ieugen.fileserver.config;


import com.fasterxml.jackson.annotation.JsonProperty;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Throwables;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class DefaultServerConfiguration {

    public static final int DEFAULT_LISTENING_PORT = 9111;
    public static final String DEFAULT_SERVER_ROOT = ".";
    public static final int DEFAULT_WORKER_THREAD_COUNT = 4;

    @Valid
    @NotNull
    @JsonProperty
    private int port = DEFAULT_LISTENING_PORT;

    @Valid
    @NotNull
    @JsonProperty
    private String root = DEFAULT_SERVER_ROOT;

    @Valid
    @NotNull
    @JsonProperty
    private int workerThreadCount = DEFAULT_WORKER_THREAD_COUNT;

    @Valid
    @NotNull
    @JsonProperty
    private String template = "index-template.html";

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        checkArgument(port > 1, "Port must be a positive integer");
        this.port = port;
    }

    public String getRoot() {
        return root;
    }

    public URI getCanonicalRoot() {
        try {
            return new File(root).getCanonicalFile().toURI();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public void setRoot(String root) {
        this.root = checkNotNull(root, "Root directory must not be null");
    }

    public int getWorkerThreadCount() {
        return workerThreadCount;
    }

    public void setWorkerThreadCount(int workerThreadCount) {
        checkArgument(workerThreadCount >= 1, "Minimum two worker threads required");
        this.workerThreadCount = workerThreadCount;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "DefaultServerConfiguration{" +
                "port=" + port +
                ", root='" + root + '\'' +
                ", workerThreadCount=" + workerThreadCount +
                '}';
    }
}
