package com.douqz.handler;

import com.douqz.HttpServer;
import com.douqz.core.Context;
import com.douqz.core.DefaultRequest;
import com.douqz.core.DefaultResponse;
import com.douqz.core.Wrapper;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yui
 */
@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final HttpServer server;

    public HttpRequestHandler(HttpServer server) {
        this.server = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        FullHttpResponse resp = this.buildResponse(ctx, msg);
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
        log.info("{}", msg);
    }

    private FullHttpResponse buildResponse(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        String uri = msg.uri();
        Context context = server.getContext();
        Wrapper wrapper = context.findMatchChild(uri);
        FullHttpResponse resp = new DefaultFullHttpResponse(
                msg.protocolVersion(),
                HttpResponseStatus.OK,
                ctx.alloc().buffer()
        );
        HttpHeaders headers = resp.headers();
        // 设置一些默认的请求头
        headers.add(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");


        if (wrapper != null) {
            DefaultRequest req = new DefaultRequest(msg);
            HttpServletResponse servletResponse = new DefaultResponse(resp);
            req.setServerPort(server.getPort());
            wrapper.getServlet().service(req, servletResponse);
        } else {
            resp.setStatus(HttpResponseStatus.NOT_FOUND);
        }
        int contentLength = resp.content().readableBytes();
        headers.add(HttpHeaderNames.CONTENT_LENGTH, contentLength);
        return resp;
    }
}
