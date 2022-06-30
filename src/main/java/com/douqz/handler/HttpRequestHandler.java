package com.douqz.handler;

import com.douqz.HttpServer;
import com.douqz.core.Context;
import com.douqz.core.DefaultRequest;
import com.douqz.core.DefaultResponse;
import com.douqz.core.Wrapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

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
        String uri = msg.uri();
        HttpMethod method = msg.method();
        String httpMethod = method.name();
        Context context = server.getContext();
        Wrapper child = context.findMatchChild(uri);
        FullHttpResponse resp = null;
        if (child != null) {
            // request
            HttpServletRequest servletRequest = new DefaultRequest(msg);
            HttpServletResponse servletResponse = new DefaultResponse();
            child.getServlet().service(servletRequest, servletResponse);
        } else {
            resp = new DefaultFullHttpResponse(
                    msg.protocolVersion(),
                    HttpResponseStatus.NOT_FOUND,
                    ctx.alloc().buffer()
            );
            // 设置头信息
            // resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            //resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        }
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
        log.info("{}", msg);
    }
}
