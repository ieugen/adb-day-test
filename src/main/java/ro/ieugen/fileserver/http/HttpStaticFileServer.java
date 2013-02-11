package ro.ieugen.fileserver.http;

import static com.google.common.base.Preconditions.checkNotNull;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.ieugen.fileserver.config.DefaultServerConfiguration;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpStaticFileServer implements Runnable, Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(HttpStaticFileServer.class);

    private final DefaultServerConfiguration configuration;
    private ServerBootstrap bootstrap;
    private ChannelGroup allChannels;

    public HttpStaticFileServer(DefaultServerConfiguration configuration) {
        this.configuration = checkNotNull(configuration, "Server configuration is null");
        this.allChannels = new DefaultChannelGroup("http-server");
    }

    @Override
    public void run() {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new HttpStaticFileServerPipelineFactory());
        LOG.info("Starting HTTP server on port {} with root {}", configuration.getPort(), configuration.getRoot());
        Channel serverChannel = bootstrap.bind(new InetSocketAddress(configuration.getPort()));
        allChannels.add(serverChannel);
    }

    @Override
    public void close() throws IOException {
        bootstrap.shutdown();
        bootstrap.getFactory().releaseExternalResources();
    }
}
