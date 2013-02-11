package ro.ieugen.http.fileserver;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpStaticFileServer implements Runnable, Closeable {

    private final int port;
    private boolean running = false;
    private ServerBootstrap bootstrap;

    public HttpStaticFileServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new HttpStaticFileServerPipelineFactory());

        bootstrap.bind(new InetSocketAddress(port));
        running = true;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void close() throws IOException {
        if (!isRunning()) {
            throw new IllegalStateException("Server is not running");
        }
        bootstrap.shutdown();
        bootstrap.getFactory().releaseExternalResources();
        running = false;
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) {
        int port = 9123;
        new HttpStaticFileServer(port).run();
    }
}
