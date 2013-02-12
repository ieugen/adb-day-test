package ro.ieugen.fileserver.config;


import com.fasterxml.jackson.annotation.JsonProperty;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Throwables;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

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

    public String getCanonicalRoot() {
        String path;
        try {
            path = new File(root).getCanonicalPath();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
        return path;
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

    @Override
    public String toString() {
        return "DefaultServerConfiguration{" +
                "port=" + port +
                ", root='" + root + '\'' +
                ", workerThreadCount=" + workerThreadCount +
                '}';
    }
}
