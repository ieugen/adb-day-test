package ro.ieugen.fileserver.http;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import static org.jboss.netty.handler.codec.http.HttpMethod.GET;
import org.jboss.netty.handler.codec.http.HttpRequest;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;

public class CheckRequestMethodHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        HttpRequest request = (HttpRequest) e.getMessage();
        if (request.getMethod() != GET) {
            HandlerUtils.sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }
        ctx.sendUpstream(e);
    }
}
