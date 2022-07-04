package com.douqz;

import com.douqz.core.Context;
import com.douqz.core.DefaultWrapper;
import com.douqz.core.Wrapper;
import com.douqz.handler.HttpRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.servlet.Servlet;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author yui
 */
@Slf4j
public class HttpServer {
    private static final LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;
    private Channel channel;
    private Context context;
    protected int port;

    public void start(int port) throws InterruptedException {
        this.port = port;
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();

        channel = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    // 连接建立后调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(LOGGING_HANDLER)
                                .addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(1024 * 1024 * 5))
                                .addLast(new HttpRequestHandler(HttpServer.this))
                        ;
                    }
                })
                .bind(new InetSocketAddress(port))
                .sync()
                .channel();
        log.info("Starting ProtocolHandler ['http-netty-{}']", port);
    }


    public Wrapper addServlet(Servlet servlet) {
        Wrapper wrapper = new DefaultWrapper(servlet);
        this.context.addChild(wrapper);
        return wrapper;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void close() {
        this.channel.close();
        this.channel.closeFuture().addListener(future -> {
            this.worker.shutdownGracefully();
            this.boss.shutdownGracefully();
        });
    }

    public int getPort() {
        return port;
    }
}
