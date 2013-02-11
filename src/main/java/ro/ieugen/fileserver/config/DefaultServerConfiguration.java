package ro.ieugen.fileserver.config;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
        this.port = port;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public int getWorkerThreadCount() {
        return workerThreadCount;
    }

    public void setWorkerThreadCount(int workerThreadCount) {
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
