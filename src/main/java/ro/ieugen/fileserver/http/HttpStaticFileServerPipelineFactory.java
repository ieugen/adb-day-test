/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package ro.ieugen.fileserver.http;

import static com.google.common.base.Preconditions.checkNotNull;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;
import ro.ieugen.fileserver.config.DefaultServerConfiguration;

public class HttpStaticFileServerPipelineFactory implements ChannelPipelineFactory {

    private final DefaultServerConfiguration defaultServerConfiguration;

    public HttpStaticFileServerPipelineFactory(DefaultServerConfiguration defaultServerConfiguration) {
        this.defaultServerConfiguration = checkNotNull(defaultServerConfiguration, "Configuration is null");
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();

        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpChunkAggregator(65536));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());

        pipeline.addLast("checkRequestMethod", new CheckRequestMethodHandler());

        pipeline.addLast("handler", new HttpStaticFileServerHandler(defaultServerConfiguration));
        return pipeline;
    }
}
